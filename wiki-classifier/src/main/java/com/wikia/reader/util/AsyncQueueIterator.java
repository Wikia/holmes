package com.wikia.reader.util;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Author: Artur Dwornik
* Date: 24.03.13
* Time: 15:29
*/
class AsyncQueueIterator<T> implements Iterator<T> {
    private static Logger logger = Logger.getLogger(AsyncQueueIterator.class.toString());
    private final AsyncQueue<T> queue;
    private boolean closed;
    private boolean fetched;
    private T nextElement;

    public AsyncQueueIterator(AsyncQueue<T> queue) {
        this.queue = queue;
        closed = false;
        fetched = false;
    }

    @Override
    public synchronized boolean hasNext() {
        if(closed) {
            return false;
        } if(!fetched) {
            queue.pollOne(new AsyncQueueListener<T>() {
                @Override
                public void receive(T element) {
                    received(element);
                }

                @Override
                public void close() {
                    closeIterator();
                }
            });
            while(!closed && !fetched) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, "Sync wait interrupted.", e);
                    closed = true;
                    return false;
                }
            }
            if(closed) return false;
            return true;
        } else {
            return true;
        }
    }

    private synchronized void received(T element) {
        if(fetched) throw new IllegalStateException();
        fetched = true;
        nextElement = element;
        this.notify();
    }

    private synchronized void closeIterator() {
        closed = true;
        this.notify();
    }

    @Override
    public synchronized T next() {
        if(hasNext()) {
            fetched = false;
            T tmp = nextElement;
            nextElement = null;
            return tmp;
        }
        throw new IllegalStateException("hasNext() is false.");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
