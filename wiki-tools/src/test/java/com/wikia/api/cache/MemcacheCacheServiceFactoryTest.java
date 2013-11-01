package com.wikia.api.cache;


import net.rubyeye.xmemcached.utils.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;

public class MemcacheCacheServiceFactoryTest {
    private static Logger logger = LoggerFactory.getLogger(MemcacheCacheServiceFactoryTest.class);
    private MemcacheCacheServiceFactory memcacheCacheServiceFactoryMock;
    private MemcacheCacheServiceFactory memcacheCacheServiceFactoryReal;
    private MemcachedClient newClientMock;

    @BeforeMethod
    public void setUp() throws Exception {
        newClientMock = mock(MemcachedClient.class);
        memcacheCacheServiceFactoryMock = spy(new MemcacheCacheServiceFactory());
        Random random = new Random();
        String randomHost = "prefix" + random.nextLong() + ":" + (1 + random.nextInt(30000));
        memcacheCacheServiceFactoryMock.setAddresses(AddrUtil.getAddresses(randomHost));
        doReturn(newClientMock).when(memcacheCacheServiceFactoryMock).createNew();

        memcacheCacheServiceFactoryReal = new MemcacheCacheServiceFactory();
    }

    @Test
    public void testGetSameConfig() throws Exception {
        CacheService client = memcacheCacheServiceFactoryMock.get();
        Assert.assertNotNull(client);

        CacheService client2 = memcacheCacheServiceFactoryMock.get();
        Assert.assertNotNull(client2);

        verify(memcacheCacheServiceFactoryMock).createNew();
    }

    @Test
    public void testGetDifferentConfig() throws Exception {
        CacheService client = memcacheCacheServiceFactoryMock.get();
        Assert.assertNotNull(client);

        memcacheCacheServiceFactoryMock.setSeconds(123453);
        CacheService client2 = memcacheCacheServiceFactoryMock.get();
        Assert.assertNotNull(client2);

        verify(memcacheCacheServiceFactoryMock, times(2)).get();
        verify(memcacheCacheServiceFactoryMock, times(2)).createNew();
    }

    @Test
    public void testGetDifferentHost() throws Exception {
        CacheService client = memcacheCacheServiceFactoryMock.get();
        Assert.assertNotNull(client);

        memcacheCacheServiceFactoryMock.getAddresses().add(AddrUtil.getOneAddress("foo.host:12312"));
        CacheService client2 = memcacheCacheServiceFactoryMock.get();
        Assert.assertNotNull(client2);

        verify(memcacheCacheServiceFactoryMock, times(2)).get();
        verify(memcacheCacheServiceFactoryMock, times(2)).createNew();
    }

    @Test
    public void testGetAddresses() throws Exception {
        List<InetSocketAddress> addresses = memcacheCacheServiceFactoryReal.getAddresses();

        Assert.assertEquals(addresses.size(), 1); // default host
        Assert.assertEquals(addresses.get(0), AddrUtil.getOneAddress("127.0.0.1:11211"));
    }

    @Test
    public void testSetAddresses() throws Exception {
        List<InetSocketAddress> addresses = AddrUtil.getAddresses("a:123 b:321 c:919");
        memcacheCacheServiceFactoryReal.setAddresses(addresses);

        Assert.assertEquals(memcacheCacheServiceFactoryReal.getAddresses(), addresses);
    }

    @Test
    public void testGetSeconds() throws Exception {
        Assert.assertEquals(memcacheCacheServiceFactoryReal.getSeconds(), 3600 * 24 * 30); // default value is one month
    }

    @Test
    public void testSetSeconds() throws Exception {
        memcacheCacheServiceFactoryReal.setSeconds(13);
        Assert.assertEquals(memcacheCacheServiceFactoryReal.getSeconds(), 13);
    }
}
