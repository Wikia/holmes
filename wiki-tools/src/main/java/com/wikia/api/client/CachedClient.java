package com.wikia.api.client;/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 00:17
 */

import com.wikia.api.cache.CacheFallbackFetcher;
import com.wikia.api.cache.CacheService;
import com.wikia.api.response.AllPagesQueryResponseWrapper;
import com.wikia.api.response.RevisionsQueryResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CachedClient implements Client {
    private static Logger logger = LoggerFactory.getLogger(CachedClient.class);
    private Client client;
    private CacheService cacheService;

    public CachedClient(Client client, CacheService cacheService) {
        this.client = client;
        this.cacheService = cacheService;
    }

    @Override
    public RevisionsQueryResponseWrapper getRevisions(final long pageId) throws IOException {
        return cacheService.get("getRevisions:" + pageId, new CacheFallbackFetcher<RevisionsQueryResponseWrapper>() {
            @Override
            public RevisionsQueryResponseWrapper call() throws IOException {
                return client.getRevisions(pageId);
            }
        });
    }

    @Override
    public RevisionsQueryResponseWrapper getRevisions(final String title) throws IOException {
        return cacheService.get("getRevisions:title:" + title, new CacheFallbackFetcher<RevisionsQueryResponseWrapper>() {
            @Override
            public RevisionsQueryResponseWrapper call() throws IOException {
                return client.getRevisions(title);
            }
        });
    }

    @Override
    public AllPagesQueryResponseWrapper getAllPages(final long count, final String apFrom) throws IOException {
        return cacheService.get("getAllPages:" + count + ":" + apFrom, new CacheFallbackFetcher<AllPagesQueryResponseWrapper>() {
            @Override
            public AllPagesQueryResponseWrapper call() throws IOException {
                return client.getAllPages(count, apFrom);
            }
        });
    }
}
