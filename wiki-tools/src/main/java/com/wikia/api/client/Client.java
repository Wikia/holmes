package com.wikia.api.client;

import com.wikia.api.client.response.AllPagesQueryResponseWrapper;
import com.wikia.api.client.response.LinksResponseWrapper;
import com.wikia.api.client.response.RevisionsQueryResponseWrapper;

import java.io.IOException;

/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 18:42
 */

/**
 * Thin client for some basic wiki api features
 * @see com.wikia.api.service.PageService
 */
public interface Client {
    RevisionsQueryResponseWrapper getRevisions(long pageId) throws IOException;
    RevisionsQueryResponseWrapper getRevisions(String title) throws IOException;

    AllPagesQueryResponseWrapper getAllPages(long count, String apFrom) throws IOException;

    // /w/api.php?action=query&prop=links&format=json&pllimit=500&titles=Albert_Einstein&redirects=
    LinksResponseWrapper getLinks(long pageId, String continueFrom) throws IOException;
}
