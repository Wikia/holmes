package com.wikia.api.service;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.wikia.api.client.Client;
import com.wikia.api.client.response.AllPagesPage;
import com.wikia.api.client.response.AllPagesQueryResponseWrapper;
import com.wikia.api.client.response.RevisionsQueryPage;
import com.wikia.api.client.response.RevisionsQueryResponseWrapper;
import com.wikia.api.model.LazyPage;
import com.wikia.api.model.PageInfo;
import com.wikia.api.model.builder.LazyPageBuilder;
import com.wikia.api.util.prefetch.PrefetchQueueTask;
import com.wikia.api.util.prefetch.PrefetchQueueTaskResponse;
import com.wikia.api.util.prefetch.PrefetchingIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class PageServiceImpl implements PageService {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(PageServiceImpl.class);
    private final Client client;
    private final LazyPageBuilder lazyPageBuilder;
    private final ListeningExecutorService executorService;

    public PageServiceImpl(Client client, LazyPageBuilder lazyPageBuilder, ListeningExecutorService executorService) {
        this.client = client;
        this.lazyPageBuilder = lazyPageBuilder;
        this.executorService = executorService;
    }

    @Override
    public PageInfo getPage(long pageId) throws IOException {
        RevisionsQueryResponseWrapper revisionsQueryResponseWrapper = client.getRevisions(pageId);
        if( revisionsQueryResponseWrapper == null ) return null;
        verifyRevisionsQueryResponseWrapper(revisionsQueryResponseWrapper);
        if( revisionsQueryResponseWrapper.getQueryResponse().getPages().isEmpty() ) return null;
        RevisionsQueryPage responsePage
                = revisionsQueryResponseWrapper.getQueryResponse().getPages().get(pageId);

        return processSingleRevisionResponse( responsePage );
    }

    @Override
    public PageInfo getPage(String title) throws IOException {
        RevisionsQueryResponseWrapper revisionsQueryResponseWrapper = client.getRevisions(title);
        if( revisionsQueryResponseWrapper == null ) return null;
        verifyRevisionsQueryResponseWrapper(revisionsQueryResponseWrapper);
        if( revisionsQueryResponseWrapper.getQueryResponse().getPages().isEmpty() ) return null;
        RevisionsQueryPage responsePage
                = revisionsQueryResponseWrapper.getQueryResponse().getPages().values().iterator().next();

        return processSingleRevisionResponse( responsePage );
    }

    private void verifyRevisionsQueryResponseWrapper(RevisionsQueryResponseWrapper revisionsQueryResponseWrapper) throws IOException {
        if( revisionsQueryResponseWrapper.getQueryResponse() == null ) {
            //  This should be enforced by client
            throw new IOException("Format error: revisionsQueryResponseWrapper.getQueryResponse() == null");
        }
        if( revisionsQueryResponseWrapper.getQueryResponse().getPages() == null ) {
            //  This should be enforced by client
            throw new IOException("Format error: revisionsQueryResponseWrapper.getQueryResponse().getPages() == null");
        }
    }

    @Override
    public Iterable<PageInfo> getPages() {
        return new PageIterable(client);
    }

    private PageInfo processSingleRevisionResponse( RevisionsQueryPage responsePage ) {
        if (   responsePage != null
                && responsePage.getRevisions() != null
                && responsePage.getRevisions().size() > 0) {
            return this.lazyPageBuilder.byId( responsePage.getPageId() )
                    .setId( responsePage.getPageId() )
                    .setNamespace( responsePage.getNamespace() )
                    .setTitle( responsePage.getTitle() )
                    .setWikiText(responsePage.getRevisions().get(0).getContent())
                    .build();
        }
        return null;
    }

    public class PageIterable implements Iterable<PageInfo> {
        private final long pageSize = 50;
        public PageIterable(Client client) {
            this.client = client;
        }

        private Client client;

        @Override
        public Iterator<PageInfo> iterator() {
            return new PrefetchingIterator<>(
                    MoreExecutors.listeningDecorator(
                            executorService), fetchPageGroup(null));
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
                        page.setTitle(responsePage.getTitle());
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
}
