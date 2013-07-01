package com.wikia.api.client;/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 00:17
 */

import com.wikia.api.cache.CacheFallbackFetcher;
import com.wikia.api.cache.CacheService;
import com.wikia.api.client.response.AllPagesQueryResponseWrapper;
import com.wikia.api.client.response.LinksResponseWrapper;
import com.wikia.api.client.response.RevisionsQueryResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class CachingClientWrapper implements Client {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(CachingClientWrapper.class);
    private static final String cachePrefix = "CachingClientWrapper:";
    private final Client client;
    private final CacheService cacheService;
    private final URL wikiDomain;

    public CachingClientWrapper(Client client, CacheService cacheService, URL wikiDomain) {
        this.client = client;
        this.cacheService = cacheService;
        this.wikiDomain = wikiDomain;
    }

    @Override
    public RevisionsQueryResponseWrapper getRevisions(final long pageId) throws IOException {
        return cacheService.get(String.format("%s%d", buildKeyPrefix("getRevisions:"), pageId), new CacheFallbackFetcher<RevisionsQueryResponseWrapper>() {
            @Override
            public RevisionsQueryResponseWrapper call() throws IOException {
                return client.getRevisions(pageId);
            }
        });
    }

    @Override
    public RevisionsQueryResponseWrapper getRevisions(final String title) throws IOException {
        return cacheService.get(String.format("%s%s", buildKeyPrefix("getRevisions:title:"), title), new CacheFallbackFetcher<RevisionsQueryResponseWrapper>() {
            @Override
            public RevisionsQueryResponseWrapper call() throws IOException {
                return client.getRevisions(title);
            }
        });
    }

    @Override
    public AllPagesQueryResponseWrapper getAllPages(final long count, final String apFrom) throws IOException {
        return cacheService.get(String.format("%s%d:%s", buildKeyPrefix("getAllPages:"), count, apFrom), new CacheFallbackFetcher<AllPagesQueryResponseWrapper>() {
            @Override
            public AllPagesQueryResponseWrapper call() throws IOException {
                return client.getAllPages(count, apFrom);
            }
        });
    }

    @Override
    public LinksResponseWrapper getLinks(final long pageId, final String continueFrom) throws IOException {
        return cacheService.get(String.format("%s%d%s", buildKeyPrefix("getLinks:"), pageId, continueFrom), new CacheFallbackFetcher<LinksResponseWrapper>() {
            @Override
            public LinksResponseWrapper call() throws IOException {
                return client.getLinks(pageId, continueFrom);
            }
        });
    }

    protected String buildKeyPrefix(String methodName) {
        return cachePrefix + wikiDomain.getHost() + ":" + wikiDomain.getPort() + ":" + methodName;
    }
}
