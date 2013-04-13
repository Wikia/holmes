package com.wikia.reader.weka;

import com.wikia.reader.input.TextChunk;
import weka.core.Instances;

import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 05.04.13
 * Time: 01:37
 */
public class VectorInstanceSetBuilderImpl implements VectorInstanceSetBuilder {
    private static Logger logger = Logger.getLogger(VectorInstanceSetBuilderImpl.class.toString());
    private InstanceSetBuilder instanceSetBuilder;
    private InstancesVectorizer vectorizer;

    public VectorInstanceSetBuilderImpl() {
        this(new InstancesVectorizer());
    }

    public VectorInstanceSetBuilderImpl(InstancesVectorizer vectorizer) {
        this(new InstanceSetBuilder(), vectorizer);
    }

    public VectorInstanceSetBuilderImpl(InstanceSetBuilder instanceSetBuilder, InstancesVectorizer vectorizer) {
        this.instanceSetBuilder = instanceSetBuilder;
        this.vectorizer = vectorizer;
    }

    @Override
    public Instances build(List<TextChunk> textChunkList) {
        Instances dataset = instanceSetBuilder.build(textChunkList);
        Instances vectorizedDataset = vectorizer.vectorize(dataset);
        return vectorizedDataset;
    }
}
