package com.wikia.api.cache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeoutException;

public class XMemcachedCacheService implements CacheService {
    private static Logger logger = LoggerFactory.getLogger(XMemcachedCacheService.class);
    private MemcachedClient client;
    private int seconds;

    public XMemcachedCacheService(MemcachedClient client, int seconds) {
        this.client = client;
        this.seconds = seconds;
    }

    @Override
    public <T> T get(String key) {
        try {
            return (T) client.get( sanitizeKey(key), seconds );
        } catch (TimeoutException e) {
            logger.warn("Cache timeout.", e);
        } catch (InterruptedException e) {
            logger.warn("Cache timeout interruption.", e);
        } catch (MemcachedException e) {
            logger.error("Cache timeout error.", e);
        }
        return null;
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
        try {
            client.set( sanitizeKey(key), seconds, value);
        } catch (TimeoutException e) {
            logger.warn("Timeout while setting cache value.", e);
        } catch (InterruptedException e) {
            logger.warn("Setting cache value interrupted.", e);
        } catch (MemcachedException e) {
            logger.error("Error while setting value", e);
        }
    }

    @Override
    public void purge(String key) {
        try {
            client.delete( sanitizeKey(key) );
        } catch (TimeoutException e) {
            logger.error("Timeout while purging cache value.", e);
        } catch (InterruptedException e) {
            logger.error("Purging cache value interrupted.", e);
        } catch (MemcachedException e) {
            logger.error("Error while purging value", e);
        }
    }

    protected String sanitizeKey(String key) {
        try {
            return URLEncoder.encode(key, "utf8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("Cannot encode cache key:" + key);
        }
    }
}
