package com.wikia.reader.weka;

import com.beust.jcommander.internal.Lists;
import com.wikia.reader.input.TextChunk;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.neighboursearch.NearestNeighbourSearch;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 03.04.13
 * Time: 23:50
 */
public class KNNModel implements Serializable {
    private static Logger logger = Logger.getLogger(KNNModel.class.toString());
    private final NearestNeighbourSearch knnSearch;
    private final Map<String, List<String>> properties;
    private int k;
    private final VectorInstanceSetBuilder vectorInstanceSetBuilder;

    public KNNModel(NearestNeighbourSearch knnSearch, Map<String, List<String>> properties) {
        this(knnSearch, properties, 5);
    }

    public KNNModel(NearestNeighbourSearch knnSearch, Map<String, List<String>> properties, int k) {
        this.knnSearch = knnSearch;
        this.properties = properties;
        this.k = k;
        vectorInstanceSetBuilder = new VectorInstanceSetBuilderImpl();
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public Instances knn(TextChunk textChunk) {
        Instances dataset = vectorInstanceSetBuilder.build(Lists.newArrayList(textChunk));
        try {
            saveArff(dataset, "c.arff");
            saveArff(knnSearch.getInstances(), "all.arff");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "", e);
        }
        Instance instance = dataset.get(0);
        try {
            Instances instances = knnSearch.kNearestNeighbours(instance, k);
            return instances;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "KNN error.", e);
            return null; // !!
        }
    }

    public List<String> getAllProperties(TextChunk textChunk) {
        Instances instances = knn(textChunk);
        /*
        try {
            saveArff(instances, "knn.arff");
            saveArff(knnSearch.getInstances(), "all.arff");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "", e);
        }
        */
        List<String> props = new ArrayList<>();
        for(Instance instance: instances) {
            String title = instance.stringValue(instances.numAttributes() - 1);
           props.addAll(properties.get(title));
        }
        return props;
    }

    private void saveArff(Instances instances, String fileName) throws IOException {
        ArffSaver saver = new ArffSaver();
        saver.setInstances(instances);
        saver.setFile(new File(fileName));
        saver.writeBatch();
    }
}
