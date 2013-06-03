package com.wikia.reader.cache;

import java.io.Serializable;

/**
 * Author: Artur Dwornik
 * Date: 22.05.13
 * Time: 21:02
 */
public interface Cache<Key extends Serializable, Value extends Serializable> {
    Value get(Key key);
    void set(Key key, Value value);
}
