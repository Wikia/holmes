package com.wikia.reader.text.data;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 00:41
 */

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.wikia.reader.input.TextChunk;
import com.wikia.reader.providers.WikiaHelper;
import com.wikia.reader.text.Helper;
import com.wikia.reader.util.ArrayUtils;
import edu.stanford.nlp.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SparseInstance;

import java.io.IOException;
import java.util.*;

public class InstanceSourceProcessor {
    private static Logger logger = LoggerFactory.getLogger(InstanceSourceProcessor.class);
    private final List<String> features;

    public InstanceSourceProcessor(List<String> features) {
        this.features = features;
    }

    /*
    public InstanceSourceProcessor(Set<String> features) {
        this.features = features;
    }
    */

    public Instances getInstances(List<InstanceSource> sourceCollection) throws IOException {
        List<Map<String,Double>> mapList = new ArrayList<>();
        for(InstanceSource source: sourceCollection) {
            TextChunk chunk = WikiaHelper.fetch(source.getWikiRoot().toString(), source.getTitle());
            Map<String,Double> vector = Helper.vectorize(chunk);
            mapList.add(vector);
        }

        removeSparse(mapList, 0.2);

        ArrayList<Attribute> attributes = getAllAttributes(mapList);
        Attribute classAttribute = new Attribute("__CLAS__");
        attributes.add(classAttribute);
        Instances instances = new Instances("WikiaSet", attributes, sourceCollection.size());
        instances.setClass(classAttribute);

        HashMap<String, Attribute> map = new HashMap<>();
        for (Attribute attribute: attributes) {
            map.put(attribute.name(), attribute);
        }

        for (int i=0; i<mapList.size(); i++) {
            Map<String,Double> vector = mapList.get(i);
            InstanceSource instanceSource = sourceCollection.get(i);
            SparseInstance instance = buildInstance(attributes, classAttribute, instances, map, vector, instanceSource);
            instances.add(instance);
        }
        return instances;
    }

    private void removeSparse(List<Map<String, Double>> mapList, double v) {
        int minCount = (int) Math.round(mapList.size() * v);

        HashMultiset<String> termSet = HashMultiset.create();
        for(Map<String,Double> vector: mapList) {
            termSet.addAll(vector.keySet());
        }
        Iterator<Multiset.Entry<String>> entryIterator =
                termSet.entrySet().iterator();
        while ( entryIterator.hasNext() ) {
            Multiset.Entry<String> stringEntry = entryIterator.next();
            if( stringEntry.getCount() < minCount ) {
                entryIterator.remove();
            }
        }
        for (Map<String,Double> vector: mapList) {
            Iterator<Map.Entry<String,Double>> mapIterator
                    = vector.entrySet().iterator();
            while ( mapIterator.hasNext() ) {
                Map.Entry<String, Double> mapEntry = mapIterator.next();
                if( !termSet.contains(mapEntry.getKey()) ) {
                    mapIterator.remove();
                }
            }
        }
        logger.info("Terms count." + termSet.size());
    }

    private SparseInstance buildInstance(
              ArrayList<Attribute> attributes
            , Attribute classAttribute
            , Instances instances
            , HashMap<String, Attribute> map
            , Map<String, Double> vector
            , InstanceSource instanceSource) {
        int count = vector.size()+1;
        double[] values = new double[count];
        int[] indices = new int[count];
        int i = 0;
        //List<Map.Entry<String,Double>> entries = new ArrayList<>(vector.entrySet());
        //Collections.sort(entries, new Map.En);
        for (Map.Entry<String, Double> entry:vector.entrySet()) {
            indices[i] = map.get(entry.getKey()).index();
            values[i] = entry.getValue();
            i++;
        }
        indices[i] = classAttribute.index();
        values[i] = getFeature(instanceSource.getFeatures());
        ArrayUtils.sortBoth(indices, values);

        SparseInstance instance = new SparseInstance(1.0, values, indices, attributes.size());
        instance.setDataset(instances);
        return instance;
    }

    private Double getFeature(Set<String> instanceFeatures) {
        for(int i=0; i<features.size(); i++) {
            if( instanceFeatures.contains(features.get(i)) )
                return (double)i + 1;
        }
        return (double)features.size() + 1;
    }

    private ArrayList<Attribute> getAllAttributes(List<Map<String, Double>> mapList) {
        HashSet<String> names = new HashSet<>();
        for (Map<String, Double> map: mapList) {
            names.addAll(map.keySet());
        }
        ArrayList<Attribute> attributes = new ArrayList<>();
        for(String name: names) {
            attributes.add(new Attribute(name));
        }
        return attributes;
    }
}
