package com.wikia.api.cache;

import java.io.IOException;


public interface CacheServiceFactory {
    CacheService get() throws IOException;
}
