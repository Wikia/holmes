package com.wikia.reader.weka;

import com.wikia.reader.input.TextChunk;
import weka.core.Instances;

import java.util.List;

/**
 * Author: Artur Dwornik
 * Date: 05.04.13
 * Time: 01:44
 */
public interface VectorInstanceSetBuilder {
    Instances build(List<TextChunk> textChunkList);
}
