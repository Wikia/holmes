package com.wikia.reader.util;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 12:54
 */
public class BasicAsyncQueue<T> implements AsyncQueue<T> {
    private static Logger logger = Logger.getLogger(BasicAsyncQueue.class.toString());
    private final Queue<AsyncQueueListener<T>> listeners = new ArrayDeque<AsyncQueueListener<T>>();
    private final Queue<T> elements = new ArrayDeque<T>();
    private boolean closed = false;

    @Override
    public synchronized void pollOne(AsyncQueueListener<T> queueListener) {
        listeners.add(queueListener);
        tryFlush();
    }

    public synchronized void pushOne(T element) {
        if( closed ) throw new IllegalStateException("Queue is closed.");
        elements.add(element);
        tryFlush();
    }

    public synchronized void close() {
        closed = true;
        tryFlush();
    }

    private synchronized void tryFlush() {
        logger.log(Level.FINER,"try flush: listeners=" + listeners.size() + " elements=" + elements.size());
        while ( closed && elements.isEmpty() && !listeners.isEmpty()  ) {
            listeners.poll().close();
        }
        while( !listeners.isEmpty() && !elements.isEmpty() ) {
            AsyncQueueListener<T> listener = listeners.poll();
            T element = elements.poll();
            listener.receive(element);
        }
        while ( closed && elements.isEmpty() && !listeners.isEmpty()  ) {
            listeners.poll().close();
        }
    }
}
