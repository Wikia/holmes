package com.wikia.reader.util;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 12:50
 */
public interface AsyncQueueListener<T> {
    void receive(T element);
    void close();
}
