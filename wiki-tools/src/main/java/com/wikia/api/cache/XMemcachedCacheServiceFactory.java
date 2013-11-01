package com.wikia.api.cache;

import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.spy.memcached.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class XMemcachedCacheServiceFactory implements CacheServiceFactory {
    private static Logger logger = LoggerFactory.getLogger(XMemcachedCacheServiceFactory.class);
    private List<InetSocketAddress> addresses = new ArrayList<>();
    private int seconds = 3600 * 24 * 30;

    public XMemcachedCacheServiceFactory() {
        addresses = AddrUtil.getAddresses("127.0.0.1:11211");
    }

    @Override
    public CacheService get() throws IOException {
        XMemcachedClientBuilder xMemcachedClientBuilder = new XMemcachedClientBuilder(addresses);
        return new XMemcachedCacheService(
                xMemcachedClientBuilder.build(), seconds
        );
    }
}
