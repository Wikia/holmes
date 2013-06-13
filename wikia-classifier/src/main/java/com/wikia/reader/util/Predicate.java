package com.wikia.reader.util;

/**
 * Author: Artur Dwornik
 * Date: 30.03.13
 * Time: 16:05
 */
public interface Predicate<T> {
    boolean evaluate(T value);
}
