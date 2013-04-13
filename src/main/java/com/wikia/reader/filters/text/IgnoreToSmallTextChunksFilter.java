package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 13.04.13
 * Time: 10:13
 */

import com.wikia.reader.filters.FilterBase;
import com.wikia.reader.input.TextChunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class IgnoreToSmallTextChunksFilter extends FilterBase<Collection<TextChunk>,Collection<TextChunk>> {
    private static Logger logger = LoggerFactory.getLogger(IgnoreToSmallTextChunksFilter.class);
    private int threshold;

    protected IgnoreToSmallTextChunksFilter() {
        this(100);
    }

    protected IgnoreToSmallTextChunksFilter(int threshold) {
        super((Class<Collection<TextChunk>>) (Class) Collection.class
            , (Class<Collection<TextChunk>>) (Class) Collection.class);
        this.threshold = threshold;
    }

    @Override
    protected Collection<TextChunk> doFilter(Collection<TextChunk> params) {
        List<TextChunk> textChunkList = new ArrayList<>();
        for( TextChunk textChunk: params) {
            if( textChunk.getWikiText().length() < threshold ) {
                logger.info("ok");
                textChunkList.add(textChunk);
            } else {
                logger.info("ignore");
            }
        }
        return textChunkList;
    }
}
