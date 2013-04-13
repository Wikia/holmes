package com.wikia.reader.providers.api;

import com.wikia.reader.input.WikiTextChunkParser;
import com.wikia.reader.providers.CachedWikiTextReader;
import com.wikia.reader.providers.Provider;
import com.wikia.reader.providers.WikiProvider;

import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 18:09
 */
public class WikiFixedProviderFactory {
    private static Logger logger = Logger.getLogger(WikiFixedProviderFactory.class.toString());
    private final String urlRoot;
    private final Iterable<String> index;

    public WikiFixedProviderFactory(String urlRoot, Iterable<String> index) {
        this.urlRoot = urlRoot;
        this.index = index;
    }

    public Provider get() {
        return new WikiProvider(
                  new WikiFixedIndexReader(index)
                , new CachedWikiTextReader(new WikiApiTextReader(urlRoot), urlRoot)
                , new WikiTextChunkParser());
    }
}
