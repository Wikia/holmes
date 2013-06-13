package com.wikia.api.service;/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 17:11
 */

import com.google.common.util.concurrent.MoreExecutors;
import com.wikia.api.client.Client;
import com.wikia.api.prefetch.PrefetchQueueTask;
import com.wikia.api.prefetch.PrefetchQueueTaskResponse;
import com.wikia.api.prefetch.PrefetchingIterator;
import com.wikia.api.response.AllPagesPage;
import com.wikia.api.response.AllPagesQueryResponseWrapper;
import com.wikia.api.response.RevisionsQueryPage;
import com.wikia.api.response.RevisionsQueryResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

public class PageIterable implements Iterable<Page> {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(PageIterable.class);
    private final long pageSize = 50;
    public PageIterable(Client client) {
        this.client = client;
    }

    private Client client;

    @Override
    public Iterator<Page> iterator() {
        return new PrefetchingIterator<>(
                MoreExecutors.listeningDecorator(
                        Executors.newFixedThreadPool(8)), fetchPageGroup(null));
    }

    protected PrefetchQueueTask<Page> fetchPageGroup(final String from) {
        return new PrefetchQueueTask<Page>() {
            @Override
            public PrefetchQueueTaskResponse<Page> call() throws Exception {
                PrefetchQueueTaskResponse<Page> response = new PrefetchQueueTaskResponse<>();
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

    protected PrefetchQueueTask<Page> fetchPage(final long id) {
        return new PrefetchQueueTask<Page>() {
            @Override
            public PrefetchQueueTaskResponse<Page> call() throws Exception {
                PrefetchQueueTaskResponse<Page> response = new PrefetchQueueTaskResponse<>();
                RevisionsQueryResponseWrapper revisions = client.getRevisions(id);
                RevisionsQueryPage responsePage = revisions.getQueryResponse().getPages().get(id);
                if (   responsePage != null
                        && responsePage.getRevisions() != null
                        && responsePage.getRevisions().size() > 0) {
                    Page page = new Page();
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
