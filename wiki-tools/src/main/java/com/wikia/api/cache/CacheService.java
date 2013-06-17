package com.wikia.api.cache;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Author: Artur Dwornik
 * Date: 15.06.13
 * Time: 23:22
 */
public interface CacheService {
    <T> T get(String key);
    <T> T get(String key, CacheFallbackFetcher<T> fallback) throws IOException;
    <T> void set(String key, T value);
    void purge(String key);
}