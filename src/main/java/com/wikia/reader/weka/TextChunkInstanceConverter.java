package com.wikia.reader.weka;

import com.wikia.reader.input.TextChunk;
import weka.core.Attribute;
import weka.core.Instance;

import java.util.ArrayList;

/**
 * Author: Artur Dwornik
 * Date: 04.04.13
 * Time: 08:51
 */
@Deprecated
public interface TextChunkInstanceConverter {
    ArrayList<Attribute> getAttributes();
    Instance getInstance(TextChunk textChunk);
}
