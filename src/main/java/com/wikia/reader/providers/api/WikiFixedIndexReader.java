package com.wikia.reader.providers.api;

import com.wikia.reader.providers.WikiIndexReader;
import com.wikia.reader.util.AsyncQueue;
import com.wikia.reader.util.BasicAsyncQueue;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 18:06
 */
public class WikiFixedIndexReader implements WikiIndexReader {
    private final static Logger logger = Logger.getLogger(WikiFixedIndexReader.class.toString());
    private final Iterable<String> index;

    public WikiFixedIndexReader(Iterable<String> index) {
        this.index = index;
    }

    @Override
    public AsyncQueue<String> getIndex() throws IOException {
        BasicAsyncQueue<String> queue = new BasicAsyncQueue<String>();
        for(String title: index) {
            queue.pushOne(title);
        }
        queue.close();
        return queue;
    }
}
