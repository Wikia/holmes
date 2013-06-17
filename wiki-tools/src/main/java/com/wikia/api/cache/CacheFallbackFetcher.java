package com.wikia.api.cache;

import java.io.IOException;

/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 00:24
 */

public interface CacheFallbackFetcher<T> {
    T call() throws IOException;
}
