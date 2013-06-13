package com.wikia.reader.input;

import com.wikia.reader.input.sweble.SwebleHelper;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 02:17
 */
public class WikiTextChunkParser implements TextChunkParser {
    @Override
    public TextChunk parse(String title,String text) {
        return new WikiTextChunk(text, SwebleHelper.render(text), title);
    }
}
