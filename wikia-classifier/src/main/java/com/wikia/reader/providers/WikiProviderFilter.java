package com.wikia.reader.providers;

import com.wikia.reader.input.TextChunk;
import com.wikia.reader.util.AsyncQueue;
import com.wikia.reader.util.AsyncQueues;
import com.wikia.reader.util.Predicate;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 30.03.13
 * Time: 15:55
 */
public class WikiProviderFilter implements Provider {
    private static Logger logger = Logger.getLogger(WikiProviderFilter.class.toString());
    private final Provider provider;

    public WikiProviderFilter(Provider provider) {
        this.provider = provider;
    }

    @Override
    public AsyncQueue<TextChunk> provide() throws IOException {
        return AsyncQueues.where(provider.provide(), new Predicate<TextChunk>() {
            @Override
            public boolean evaluate(TextChunk value) {
                return value.getWikiText().length() > 100;
            }
        });
    }
}
