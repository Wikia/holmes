package com.wikia.reader.util;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 12:49
 */
public interface AsyncQueue<T> {
    void pollOne(AsyncQueueListener<T> queueListener);
}
