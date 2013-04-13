package com.wikia.reader.input;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 02:16
 */
public interface TextChunkParser {
    TextChunk parse(String title, String text);
}
