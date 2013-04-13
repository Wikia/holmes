package com.wikia.reader.weka;

import com.wikia.reader.input.TextChunk;

import java.util.List;
import java.util.Map;

/**
 * Author: Artur Dwornik
 * Date: 04.04.13
 * Time: 09:06
 */
public interface KNNModelFactory {
    KNNModel get(List<TextChunk> textChunkList, Map<String, List<String>> properties);
}
