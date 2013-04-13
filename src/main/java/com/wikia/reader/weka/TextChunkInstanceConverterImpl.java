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
 * Date: 04.04.13
 * Time: 08:52
 */
@Deprecated
public class TextChunkInstanceConverterImpl implements TextChunkInstanceConverter {
    private static Logger logger = Logger.getLogger(TextChunkInstanceConverterImpl.class.toString());
    private Instances dataset;

    public TextChunkInstanceConverterImpl(Instances dataset) {
        if(dataset == null) {
            dataset = fakeDataset();
        }
        this.dataset = dataset;
    }

    private Instances fakeDataset() {
        Instances instances = new Instances("fake", getAttributes(), 100);
        return instances;
    }

    @Override
    public ArrayList<Attribute> getAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("title", (List<String>) null));
        attributes.add(new Attribute("weka", (List<String>) null));
        return attributes;
    }

    @Override
    public Instance getInstance(TextChunk textChunk) {
        DenseInstance instance = new DenseInstance(2);
        instance.setDataset(dataset);
        instance.setValue(0, textChunk.getTitle());
        instance.setValue(1, textChunk.getSgml());
        return instance;
    }
}
