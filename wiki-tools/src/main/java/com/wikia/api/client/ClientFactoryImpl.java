package com.wikia.api.client;/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 00:11
 */

import com.wikia.api.cache.CacheServiceFactory;
import com.wikia.api.cache.MemcacheCacheServiceFactory;
import com.wikia.api.json.JsonClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class ClientFactoryImpl implements ClientFactory {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ClientFactoryImpl.class);
    private CacheServiceFactory cacheServiceFactory = new MemcacheCacheServiceFactory();

    @Override
    public Client get( URL apiRoot ) throws IOException {
        return new CachingClientWrapper(
                new ClientImpl(new JsonClientImpl(), apiRoot)
                , cacheServiceFactory.get(), apiRoot);
    }
}
