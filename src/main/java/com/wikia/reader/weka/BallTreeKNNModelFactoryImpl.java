package com.wikia.reader.weka;

import com.wikia.reader.input.TextChunk;
import weka.core.Instances;
import weka.core.neighboursearch.BallTree;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 04.04.13
 * Time: 09:08
 */
public class BallTreeKNNModelFactoryImpl implements KNNModelFactory {
    private static Logger logger = Logger.getLogger(BallTreeKNNModelFactoryImpl.class.toString());
    private VectorInstanceSetBuilderImpl vectorInstanceSetBuilder = new VectorInstanceSetBuilderImpl();

    @Override
    public KNNModel get(List<TextChunk> textChunkList, Map<String, List<String>> properties) {
        Instances wordVectors = vectorInstanceSetBuilder.build(textChunkList);
        BallTree search = new BallTree(wordVectors);
        return new KNNModel(search, properties);
    }
}
