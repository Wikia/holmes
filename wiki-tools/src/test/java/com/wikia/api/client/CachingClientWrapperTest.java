package com.wikia.api.client;


import com.wikia.api.cache.CacheFallbackFetcher;
import com.wikia.api.cache.CacheService;
import com.wikia.api.client.response.AllPagesQueryResponseWrapper;
import com.wikia.api.client.response.LinksResponseWrapper;
import com.wikia.api.client.response.RevisionsQueryResponseWrapper;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URL;
import java.util.Random;

import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class CachingClientWrapperTest {
    private Client client;
    private CacheService successfulCacheService;
    private CacheService failedCacheService;
    private URL url;
    private CachingClientWrapper successfulCachingClientWrapper;
    private CachingClientWrapper failedCachingClientWrapper;
    private long magicNumber;
    private RevisionsQueryResponseWrapper revisionsResponseMock;
    private AllPagesQueryResponseWrapper allPagesMock;
    private LinksResponseWrapper linksMock;

    @BeforeMethod
    public void setUp() throws Exception {
        revisionsResponseMock = mock(RevisionsQueryResponseWrapper.class);
        allPagesMock = mock(AllPagesQueryResponseWrapper.class);
        linksMock = mock(LinksResponseWrapper.class);
        client = mock(Client.class);
        when(client.getRevisions(anyLong())).thenReturn(revisionsResponseMock);
        when(client.getRevisions(anyString())).thenReturn(revisionsResponseMock);
        when(client.getAllPages(anyLong(), anyString())).thenReturn(allPagesMock);
        when(client.getLinks(anyLong(), anyString())).thenReturn(linksMock);
        successfulCacheService = mock(CacheService.class);
        when(successfulCacheService.get(anyString(), any(CacheFallbackFetcher.class))).thenReturn(revisionsResponseMock);
        failedCacheService = mock(CacheService.class);
        when(failedCacheService.get(anyString(), any(CacheFallbackFetcher.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return ((CacheFallbackFetcher)invocationOnMock.getArguments()[1]).call();
            }
        });

        url = URI.create("http://random-wiki-" + (new Random()) + ".pl").toURL();
        successfulCachingClientWrapper = new CachingClientWrapper(client, successfulCacheService, url);
        failedCachingClientWrapper = new CachingClientWrapper(client, failedCacheService, url);
        magicNumber = new Random().nextInt(1000) + 1;
    }

    @Test
    public void testGetRevisionsByTitleCacheHit() throws Exception {
        RevisionsQueryResponseWrapper revisions = successfulCachingClientWrapper.getRevisions("title-123");

        Assert.assertEquals(revisions, revisionsResponseMock);
        verify(successfulCacheService).get(anyString(), any(CacheFallbackFetcher.class));
        verify(client, never()).getRevisions(anyString());
    }

    @Test
    public void testGetRevisionsByTitleCacheMiss() throws Exception {
        RevisionsQueryResponseWrapper revisions = failedCachingClientWrapper.getRevisions("title-123");

        Assert.assertEquals(revisions, revisionsResponseMock);
        verify(failedCacheService).get(anyString(), any(CacheFallbackFetcher.class));
        verify(client).getRevisions("title-123");
    }

    @Test
    public void testGetRevisionsByIdCacheHit() throws Exception {
        RevisionsQueryResponseWrapper revisions = successfulCachingClientWrapper.getRevisions(magicNumber);

        Assert.assertEquals(revisions, revisionsResponseMock);
        verify(successfulCacheService).get(anyString(), any(CacheFallbackFetcher.class));
        verify(client, never()).getRevisions(anyLong());
    }

    @Test
    public void testGetRevisionsByIdCacheMiss() throws Exception {
        RevisionsQueryResponseWrapper revisions = failedCachingClientWrapper.getRevisions(magicNumber);

        Assert.assertEquals(revisions, revisionsResponseMock);
        verify(failedCacheService).get(anyString(), any(CacheFallbackFetcher.class));
        verify(client).getRevisions(magicNumber);
    }

    @Test
    public void testGetAllPagesCacheHit() throws Exception {
        // Override default setup
        successfulCacheService = mock(CacheService.class);
        when(successfulCacheService.get(anyString(), any(CacheFallbackFetcher.class))).thenReturn(allPagesMock);
        successfulCachingClientWrapper = new CachingClientWrapper(client, successfulCacheService, url);

        AllPagesQueryResponseWrapper allPages = successfulCachingClientWrapper.getAllPages(magicNumber, "asd");

        Assert.assertEquals(allPages, allPagesMock);
        verify(successfulCacheService).get(anyString(), any(CacheFallbackFetcher.class));
        verify(client, never()).getAllPages(magicNumber, "asd");
    }

    @Test
    public void testGetAllPagesCacheMiss() throws Exception {
        AllPagesQueryResponseWrapper allPages = failedCachingClientWrapper.getAllPages(magicNumber, "asd");

        Assert.assertEquals(allPages, allPagesMock);
        verify(failedCacheService).get(anyString(), any(CacheFallbackFetcher.class));
        verify(client).getAllPages(magicNumber, "asd");
    }

    @Test
    public void testGetLinksHit() throws Exception {
        // Override default setup
        successfulCacheService = mock(CacheService.class);
        when(successfulCacheService.get(anyString(), any(CacheFallbackFetcher.class))).thenReturn(linksMock);
        successfulCachingClientWrapper = new CachingClientWrapper(client, successfulCacheService, url);

        LinksResponseWrapper links = successfulCachingClientWrapper.getLinks(magicNumber, "asd");

        Assert.assertEquals(links, linksMock);
        verify(successfulCacheService).get(anyString(), any(CacheFallbackFetcher.class));
        verify(client, never()).getLinks(magicNumber, "asd");
    }

    @Test
    public void testGetLinksMiss() throws Exception {
        LinksResponseWrapper links = failedCachingClientWrapper.getLinks(magicNumber, "asd");

        Assert.assertEquals(links, linksMock);
        verify(failedCacheService).get(anyString(), any(CacheFallbackFetcher.class));
        verify(client).getLinks(magicNumber, "asd");
    }
}
