package com.wikia.api.cache;
/**
 * Author: Artur Dwornik
 * Date: 15.06.13
 * Time: 23:22
 */

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MemcacheCacheService implements CacheService {
    private static Logger logger = LoggerFactory.getLogger(MemcacheCacheService.class);
    private MemcachedClient memcachedClient;
    private int seconds;

    public MemcacheCacheService(MemcachedClient memcachedClient, int seconds) {
        this.memcachedClient = memcachedClient;
        this.seconds = seconds;
    }

    @Override
    public synchronized <T> T get(String key) {
        try {
            return (T) memcachedClient.get( sanitizeKey(key) );
        } catch (net.spy.memcached.OperationTimeoutException ex) {
            logger.warn("Get operation timed out. Try to continue.", ex);
            return null;
        }
    }

    @Override
    public synchronized <T> T get(String key, CacheFallbackFetcher<T> fallback) throws IOException {
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
    public synchronized <T> void set(String key, T value) {
        OperationFuture<Boolean> set = memcachedClient.set(sanitizeKey(key), seconds, value);
        try {
            set.get();
        } catch (InterruptedException e) {
            logger.warn("Error during update of cache.", e);
        } catch (ExecutionException e) {
            logger.warn("Error during update of cache.", e);
        }
    }

    @Override
    public void purge(String key) {
        memcachedClient.delete( sanitizeKey(key) );
    }

    protected String sanitizeKey(String key) {
        return key.replaceAll("\\s", "%");
    }
}
