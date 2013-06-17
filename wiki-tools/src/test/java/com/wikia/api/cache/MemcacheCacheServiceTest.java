package com.wikia.api.cache;
/**
 * Author: Artur Dwornik
 * Date: 17.06.13
 * Time: 02:03
 */

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static org.mockito.Mockito.*;

public class MemcacheCacheServiceTest {
    private MemcachedClient memcachedClientMock;
    private MemcacheCacheService service;
    private int defaultTimeout;
    private int magicNumber;

    @BeforeMethod
    public void setUp() throws Exception {
        magicNumber = new Random().nextInt(10000) + 1;
        defaultTimeout = new Random().nextInt(10000) + 1;

        memcachedClientMock = mock(MemcachedClient.class);
        service = new MemcacheCacheService(memcachedClientMock, defaultTimeout);
    }

    @Test
    public void testGet() throws Exception {
        service.get("foo:");

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
        when(memcachedClientMock.set("foo:", defaultTimeout, magicNumber)).thenReturn(futureMock);
        when(fetcherMock.call()).thenReturn(magicNumber);

        Object o = service.get("foo:", fetcherMock);

        verify(fetcherMock).call();
        verify(memcachedClientMock).set("foo:", defaultTimeout, magicNumber);
        Assert.assertEquals(magicNumber, o);
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
