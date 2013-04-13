package com.wikia.reader.providers;

import com.beust.jcommander.internal.Lists;
import com.wikia.reader.input.TextChunk;
import com.wikia.reader.providers.api.WikiFixedProviderFactory;
import com.wikia.reader.util.AsyncQueue;
import com.wikia.reader.util.AsyncQueues;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 10:38
 */
public class WikiaHelper {
    private static Logger logger = Logger.getLogger(WikiaHelper.class.toString());

    public static TextChunk fetch(String root, String title) throws IOException {
        List<TextChunk> chunks = fetch(root, Lists.newArrayList(title));
        if (chunks.size() != 1) return null;
        return chunks.get(0);
    }

    public static List<TextChunk> fetch(String root, List<String> titles) throws IOException {
        Provider provider = new WikiFixedProviderFactory(root, titles).get();
        AsyncQueue<TextChunk> queue = provider.provide();
        return AsyncQueues.toList(queue);
    }
}
