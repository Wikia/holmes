package com.wikia.api.cache;

import java.io.IOException;



public interface CacheFallbackFetcher<T> {
    T call() throws IOException;
}
