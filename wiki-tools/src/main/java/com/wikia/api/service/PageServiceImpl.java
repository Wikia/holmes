package com.wikia.api.service;/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 16:14
 */

import com.wikia.api.client.Client;
import com.wikia.api.response.RevisionsQueryPage;
import com.wikia.api.response.RevisionsQueryResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PageServiceImpl implements PageService {
    private static Logger logger = LoggerFactory.getLogger(PageServiceImpl.class);
    private Client client;

    public PageServiceImpl(Client client) {
        this.client = client;
    }

    @Override
    public Page getPage(long pageId) throws IOException {
        RevisionsQueryResponseWrapper revisionsQueryResponseWrapper = client.getRevisions(pageId);

        RevisionsQueryPage responsePage = revisionsQueryResponseWrapper.getQueryResponse().getPages().get(pageId);
        if (   responsePage != null
                && responsePage.getRevisions() != null
                && responsePage.getRevisions().size() > 0) {
            Page page = new Page();
            page.setId( pageId );
            page.setNamespace(responsePage.getNamespace());
            page.setTitle( responsePage.getTitle() );
            page.setWikiText(responsePage.getRevisions().get(0).getContent());
            return page;
        }
        return null;
    }

    @Override
    public Iterable<Page> getPages() {
        return new PageIterable(client);
    }
}
