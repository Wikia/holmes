package com.wikia.reader.weka;

import com.wikia.reader.input.TextChunk;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 05.04.13
 * Time: 01:32
 */
public class InstanceSetBuilder {
    private static Logger logger = Logger.getLogger(InstanceSetBuilder.class.toString());

    public InstanceSetBuilder() {
    }

    public Instances build(List<TextChunk> chunkList) {
        Instances instances = new Instances("name", getAttributes(), chunkList.size());
        instances.setClassIndex(0);
        for (TextChunk textChunk: chunkList) {
            instances.add(getInstance(textChunk, instances));
        }
        return instances;
    }

    protected ArrayList<Attribute> getAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("title", (List<String>) null));
        attributes.add(new Attribute("weka", (List<String>) null));
        return attributes;
    }

    protected Instance getInstance(TextChunk textChunk, Instances dataset) {
        DenseInstance instance = new DenseInstance(2);
        instance.setDataset(dataset);
        instance.setClassValue(textChunk.getTitle());
        instance.setValue(0, textChunk.getTitle());
        instance.setValue(1, textChunk.getSgml());
        return instance;
    }
}
