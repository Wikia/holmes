package com.wikia.api.client;

import com.wikia.api.response.AllPagesQueryResponseWrapper;
import com.wikia.api.response.RevisionsQueryResponseWrapper;

import java.io.IOException;

/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 18:42
 */
public interface Client {
    RevisionsQueryResponseWrapper getRevisions(long pageId) throws IOException;

    AllPagesQueryResponseWrapper getAllPages(long count, String apFrom) throws IOException;
}
