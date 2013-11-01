package com.wikia.api.cache;


import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MemcacheCacheService implements CacheService {
    private static Logger logger = LoggerFactory.getLogger(MemcacheCacheService.class);
    private MemcachedClient memcachedClient;
    private int seconds;

    public MemcacheCacheService(MemcachedClient memcachedClient, int seconds) {
        this.memcachedClient = memcachedClient;
        this.seconds = seconds;
    }

    @Override
    @SuppressWarnings("Unchecked")
    public <T> T get(String key) {
        try {
            return (T) memcachedClient.get( sanitizeKey(key) );
        } catch (net.spy.memcached.OperationTimeoutException ex) {
            logger.warn("Get operation timed out. Try to continue.", ex);
            return null;
        }
    }

    @Override
    public <T> T get(String key, CacheFallbackFetcher<T> fallback) throws IOException {
        T val = get( key );
        if( val == null ) {
            val = fallback.call();
            set(key, val);
            return val;
        } else {
            return val;
        }
    }

    @Override
    public <T> void set(String key, T value) {
        OperationFuture<Boolean> set = memcachedClient.set(sanitizeKey(key), seconds, value);
    }

    @Override
    public void purge(String key) {
        memcachedClient.delete( sanitizeKey(key) );
    }

    protected String sanitizeKey(String key) {
        return key.replaceAll("\\s", "%");
    }
}
