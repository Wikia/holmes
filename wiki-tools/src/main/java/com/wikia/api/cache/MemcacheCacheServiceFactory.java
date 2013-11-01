package com.wikia.api.cache;

import net.spy.memcached.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

public class MemcacheCacheServiceFactory implements CacheServiceFactory {
    private static Logger logger = LoggerFactory.getLogger(MemcacheCacheServiceFactory.class);
    private List<InetSocketAddress> addresses = AddrUtil.getAddresses("127.0.0.1:11211");
    private int seconds = 3600 * 24 * 30;
    private static Map<Object, MemcachedClient> clientPool
            = new HashMap<>();

    protected MemcachedClient createNew() throws IOException {
        return new MemcachedClient(new ConnectionFactoryBuilder()
                        .setDaemon(true)
                        .build(), addresses);
    }

    @Override
    public synchronized CacheService get() throws IOException {
        MemcachedClient client = null;
        HashSet addresSet = getKey();
        synchronized (clientPool) {
            if (clientPool.containsKey(addresSet)) {
                client = clientPool.get(addresSet);
            } else {
                client = createNew();
                clientPool.put(addresSet, client);
            }
        }
        return new MemcacheCacheService( client, seconds );
    }

    /**
     * Creates key for clientPool
     * @return
     */
    private HashSet getKey() {
        HashSet addresSet = new HashSet<>(addresses);
        addresSet.add(seconds);
        return addresSet;
    }

    public List<InetSocketAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<InetSocketAddress> addresses) {
        this.addresses = addresses;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
