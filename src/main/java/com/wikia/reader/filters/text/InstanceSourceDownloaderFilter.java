package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 20:49
 */

import com.wikia.reader.filters.FilterBase;
import com.wikia.reader.input.TextChunk;
import com.wikia.reader.providers.WikiaHelper;
import com.wikia.reader.text.data.InstanceSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class InstanceSourceDownloaderFilter extends FilterBase<InstanceSource, TextChunk> {
    private static Logger logger = LoggerFactory.getLogger(InstanceSourceDownloaderFilter.class);

    protected InstanceSourceDownloaderFilter() {
        super(InstanceSource.class, TextChunk.class);
    }

    @Override
    protected TextChunk doFilter(InstanceSource params) {
        try {
            return WikiaHelper.fetch(params.getWikiRoot().toString(), params.getTitle());
        } catch (IOException e) {
            logger.error(String.format("Cannot fetch instance %d %d"
                            ,params.getWikiRoot().toString()
                            , params.getTitle()), e);
        }
        return null;
    }
}
