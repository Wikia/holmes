package com.wikia.reader.providers.api;

import com.wikia.reader.input.WikiTextChunkParser;
import com.wikia.reader.providers.CachedWikiTextReader;
import com.wikia.reader.providers.Provider;
import com.wikia.reader.providers.WikiProvider;
import com.wikia.reader.providers.WikiProviderFilter;

import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 15:40
 */
public class WikiApiProviderFactory {
    private static Logger logger = Logger.getLogger(WikiApiProviderFactory.class.toString());
    private final String urlRoot;

    public WikiApiProviderFactory(String urlRoot) {
        this.urlRoot = urlRoot;
    }

    public Provider get() {
        Provider provider = new WikiProvider(new WikiApiIndexReader(urlRoot, 100)
                              , new CachedWikiTextReader(new WikiApiTextReader(urlRoot), urlRoot)
                              , new WikiTextChunkParser());
        return new WikiProviderFilter(provider);
    }
}
