package com.wikia.api.service;/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 17:11
 */

import com.google.common.util.concurrent.MoreExecutors;
import com.wikia.api.client.Client;
import com.wikia.api.client.response.AllPagesPage;
import com.wikia.api.client.response.AllPagesQueryResponseWrapper;
import com.wikia.api.client.response.RevisionsQueryPage;
import com.wikia.api.client.response.RevisionsQueryResponseWrapper;
import com.wikia.api.model.LazyPage;
import com.wikia.api.model.PageInfo;
import com.wikia.api.util.prefetch.PrefetchQueueTask;
import com.wikia.api.util.prefetch.PrefetchQueueTaskResponse;
import com.wikia.api.util.prefetch.PrefetchingIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

public class PageIterable implements Iterable<PageInfo> {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(PageIterable.class);
    private final long pageSize = 50;
    public PageIterable(Client client) {
        this.client = client;
    }

    private Client client;

    @Override
    public Iterator<PageInfo> iterator() {
        return new PrefetchingIterator<>(
                MoreExecutors.listeningDecorator(
                        Executors.newFixedThreadPool(8)), fetchPageGroup(null));
    }

    protected PrefetchQueueTask<PageInfo> fetchPageGroup(final String from) {
        return new PrefetchQueueTask<PageInfo>() {
            @Override
            public PrefetchQueueTaskResponse<PageInfo> call() throws Exception {
                PrefetchQueueTaskResponse<PageInfo> response = new PrefetchQueueTaskResponse<>();
                final AllPagesQueryResponseWrapper allPages = client.getAllPages(pageSize, from);

                List<AllPagesPage> responsePageList = allPages.getQueryResponse().getPages();
                for (AllPagesPage page : responsePageList) {
                    if (page.getId() == null) {
                        logger.warn("Unexpected null id.", page);
                        continue;
                    }
                    response.getNewTasks().add(fetchPage(page.getId()));
                }
                String continueTitle = null;
                if ( allPages.getQueryContinue() != null
                        && allPages.getQueryContinue().getAllPages() != null  ) {
                    continueTitle =  allPages.getQueryContinue().getAllPages().getContinueTitle();
                }
                if( continueTitle != null && responsePageList.size() > 1 ) {
                    response.getNewTasks().add(
                            fetchPageGroup(continueTitle));
                }
                return response;
            }
        };
    }

    protected PrefetchQueueTask<PageInfo> fetchPage(final long id) {
        return new PrefetchQueueTask<PageInfo>() {
            @Override
            public PrefetchQueueTaskResponse<PageInfo> call() throws Exception {
                PrefetchQueueTaskResponse<PageInfo> response = new PrefetchQueueTaskResponse<>();
                RevisionsQueryResponseWrapper revisions = client.getRevisions(id);
                RevisionsQueryPage responsePage = revisions.getQueryResponse().getPages().get(id);
                if (   responsePage != null
                        && responsePage.getRevisions() != null
                        && responsePage.getRevisions().size() > 0) {
                    LazyPage page = new LazyPage();
                    page.setId( id );
                    page.setNamespace(responsePage.getNamespace());
                    page.setTitle( responsePage.getTitle() );
                    page.setWikiText(responsePage.getRevisions().get(0).getContent());

                    response.getResults().add(page);
                } else {
                    logger.warn("Unexpected format.", revisions);
                }
                return response;
            }
        };
    }
}
