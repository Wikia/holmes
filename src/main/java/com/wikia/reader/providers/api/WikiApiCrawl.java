package com.wikia.reader.providers.api;

import com.wikia.reader.input.TextChunk;
import com.wikia.reader.input.TextChunkParser;
import com.wikia.reader.providers.WikiIndexReader;
import com.wikia.reader.providers.WikiTextReader;
import com.wikia.reader.util.AsyncQueue;
import com.wikia.reader.util.AsyncQueueListener;
import com.wikia.reader.util.BasicAsyncQueue;
import org.apache.http.concurrent.FutureCallback;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 15:00
 */
public class WikiApiCrawl {
    private static Logger logger = Logger.getLogger(WikiApiCrawl.class.toString());
    private final WikiIndexReader indexReader;
    private final WikiTextReader textReader;
    private final TextChunkParser textChunkParser;
    private final BasicAsyncQueue<TextChunk> textChunkAsyncQueue = new BasicAsyncQueue<>();
    private AsyncQueue<String> titleQueue = null;
    private long requestPoll = 3;
    private long activeRequests = 0;
    private boolean closing = false;


    public WikiApiCrawl(WikiIndexReader indexReader, WikiTextReader textReader, TextChunkParser textChunkParser) {
        this.indexReader = indexReader;
        this.textReader = textReader;
        this.textChunkParser = textChunkParser;
    }

    public synchronized void start() throws IOException {
        if(titleQueue != null) throw new IllegalStateException("Already started.");
        titleQueue = indexReader.getIndex();
        tryRequest();
    }

    private synchronized void tryRequest() {
        logger.log(Level.FINER, "tryRequest requestPoll=" + requestPoll);

        if( closing && activeRequests == 0) {
            textChunkAsyncQueue.close();
        }

        while( !closing && requestPoll > 0) {
            requestPoll--;
            titleQueue.pollOne(new AsyncQueueListener<String>() {
                @Override
                public void receive(String element) {
                    try {
                        fetchText(element);
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Fetching weka failed.", e);
                    }
                }

                @Override
                public void close() {
                    titlesQueueClosed();
                }
            });
        }
    }

    private synchronized void titlesQueueClosed() {
        closing = true;
        tryRequest();
    }

    private synchronized void fetchText(final String element) throws IOException {
        activeRequests++;
        textReader.getWikiText(element, new FutureCallback<String>() {
            @Override
            public void completed(String s) {
                try {
                    TextChunk textChunk = textChunkParser.parse(element, s);
                    textChunkAsyncQueue.pushOne(textChunk);
                } finally {
                    releaseRequestPoll();
                }
            }

            @Override
            public void failed(Exception e) {
                logger.log(Level.SEVERE, "Error while fetching wikitext.", e);
                releaseRequestPoll();
            }

            @Override
            public void cancelled() {
                logger.log(Level.SEVERE, "Fetching wikitext failed.");
                releaseRequestPoll();
            }
        });
    }

    private synchronized void releaseRequestPoll() {
        requestPoll++;
        activeRequests--;
        tryRequest();
    }


    public AsyncQueue<TextChunk> getChunkQueue() {
        return textChunkAsyncQueue;
    }
}
