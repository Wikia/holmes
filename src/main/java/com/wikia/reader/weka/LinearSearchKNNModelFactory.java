package com.wikia.reader.weka;

import com.wikia.reader.input.TextChunk;
import weka.core.Instances;
import weka.core.neighboursearch.LinearNNSearch;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 04.04.13
 * Time: 09:51
 */
public class LinearSearchKNNModelFactory implements KNNModelFactory {
    private static Logger logger = Logger.getLogger(LinearSearchKNNModelFactory.class.toString());
    private VectorInstanceSetBuilderImpl vectorInstanceSetBuilder = new VectorInstanceSetBuilderImpl();

    @Override
    public KNNModel get(List<TextChunk> textChunkList, Map<String, List<String>> properties) {
        Instances wordVectors = vectorInstanceSetBuilder.build(textChunkList);
        LinearNNSearch search = new LinearNNSearch(wordVectors);
        return new KNNModel(search, properties);
    }
}
