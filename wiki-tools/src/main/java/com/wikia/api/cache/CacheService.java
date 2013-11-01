package com.wikia.api.cache;

import java.io.IOException;


public interface CacheService {
    <T> T get(String key);
    <T> T get(String key, CacheFallbackFetcher<T> fallback) throws IOException;
    <T> void set(String key, T value);
    void purge(String key);
}