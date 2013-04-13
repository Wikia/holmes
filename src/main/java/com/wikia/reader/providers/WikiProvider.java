package com.wikia.reader.providers;

import com.wikia.reader.input.TextChunk;
import com.wikia.reader.input.TextChunkParser;
import com.wikia.reader.providers.api.WikiApiCrawl;
import com.wikia.reader.util.AsyncQueue;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 00:12
 */
public class WikiProvider implements Provider {
    private static Logger logger = Logger.getLogger(WikiProvider.class.toString());
    private final WikiIndexReader indexReader;
    private final WikiTextReader textReader;
    private final TextChunkParser textChunkParser;

    public WikiProvider(WikiIndexReader indexReader, WikiTextReader textReader, TextChunkParser textChunkParser) {
        this.indexReader = indexReader;
        this.textReader = textReader;
        this.textChunkParser = textChunkParser;
    }

    public AsyncQueue<TextChunk> provide() throws IOException {
        final WikiApiCrawl wikiApiCrawl = new WikiApiCrawl(indexReader, textReader, textChunkParser);
        wikiApiCrawl.start();
        return wikiApiCrawl.getChunkQueue();
    }
}
