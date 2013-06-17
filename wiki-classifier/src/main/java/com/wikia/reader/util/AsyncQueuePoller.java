package com.wikia.reader.util;

import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 30.03.13
 * Time: 16:07
 */
public class AsyncQueuePoller<T> implements AsyncQueueListener<T> {
    private static Logger logger = Logger.getLogger(AsyncQueuePoller.class.toString());

    private final AsyncQueue<T> queue;
    private final AsyncQueueListener<T> listener;

    public AsyncQueuePoller(AsyncQueue<T> queue, AsyncQueueListener<T> listener) {
        this.queue = queue;
        this.listener = listener;
        queue.pollOne(this);
    }

    @Override
    public void receive(T element) {
        listener.receive(element);
        queue.pollOne(this);
    }

    @Override
    public void close() {
        listener.close();
    }
}
