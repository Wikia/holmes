package com.wikia.api.cache;
/**
 * Author: Artur Dwornik
 * Date: 17.06.13
 * Time: 02:03
 */

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.mockito.Mockito.*;

public class MemcacheCacheServiceTest {
    private static Logger logger = LoggerFactory.getLogger(MemcacheCacheServiceTest.class);
    private MemcachedClient memcachedClientMock;
    private MemcacheCacheService service;
    private int defaultTimeout = 18734;

    @BeforeMethod
    public void setUp() throws Exception {
        memcachedClientMock = mock(MemcachedClient.class);
        service = new MemcacheCacheService(memcachedClientMock, defaultTimeout);
    }

    @Test
    public void testGet() throws Exception {
        Object o = service.get("foo:");

        verify(memcachedClientMock).get("foo:");
    }

    @Test
    public void testGetWithCallback() throws Exception {
        CacheFallbackFetcher fetcherMock = mock(CacheFallbackFetcher.class);
        when(memcachedClientMock.get("foo:")).thenReturn("bar");

        Object o = service.get("foo:", fetcherMock);

        Assert.assertEquals("bar", o);
    }

    @Test
    public void testGetWithCallbackFallback() throws Exception {
        CacheFallbackFetcher fetcherMock = mock(CacheFallbackFetcher.class);
        OperationFuture futureMock = mock(OperationFuture.class);
        when(memcachedClientMock.get("foo:")).thenReturn(null);
        when(memcachedClientMock.set("foo:", defaultTimeout, 14)).thenReturn(futureMock);
        when(fetcherMock.call()).thenReturn(14);

        Object o = service.get("foo:", fetcherMock);

        verify(fetcherMock).call();
        verify(memcachedClientMock).set("foo:", defaultTimeout, 14);
        Assert.assertEquals(14, o);
    }

    @Test
    public void testSet() throws Exception {
        Object val = new Object();
        OperationFuture futureMock = mock(OperationFuture.class);
        when(memcachedClientMock.set("key", defaultTimeout, val))
                .thenReturn(futureMock);

        service.set("key", val);

        verify(memcachedClientMock).set("key", defaultTimeout, val);
    }

    @Test
    public void testPurge() throws Exception {
        service.purge("faux");

        verify(memcachedClientMock).delete("faux");
    }
}
