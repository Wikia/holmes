package com.wikia.reader.cache;/**
 * Author: Artur Dwornik
 * Date: 22.05.13
 * Time: 21:21
 */

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class JcsCache<Key extends Serializable, Value extends Serializable> implements Cache<Key, Value> {
    private static Logger logger = LoggerFactory.getLogger(JcsCache.class);
    private JCS jcs;

    @Override
    public Value get(Key key) {
        return (Value) jcs.get( key );
    }

    @Override
    public void set(Key key, Value value) {
        try {
            jcs.put(key, value);
        } catch (CacheException e) {
            logger.warn("Error while JcsCache.put.", e);
        }
    }
}
