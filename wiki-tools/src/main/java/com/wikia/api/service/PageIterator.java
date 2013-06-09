package com.wikia.api.service;/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 15:46
 */

import com.google.common.util.concurrent.ListeningExecutorService;
import com.wikia.api.client.Client;
import com.wikia.api.response.AllPagesPage;
import com.wikia.api.response.AllPagesQueryResponseWrapper;
import com.wikia.api.response.RevisionsQueryPage;
import com.wikia.api.response.RevisionsQueryResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;

public class PageIterator implements Iterator<Page> {
    private static Logger logger = LoggerFactory.getLogger(PageIterator.class);
    private final Client client;
    private final ListeningExecutorService executorService;
    private final BlockingQueue<Page> queue = new ArrayBlockingQueue<>(64);
    private int pageSize = 50;
    private final Object lock = new Object();
    private long pendingTasksCount = 0;

    public PageIterator(Client client, ListeningExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }

    public void start() {
        fetchPageGroup(null);
    }

    private void fetchPageGroup(final String from) {
        synchronized (lock) {
            pendingTasksCount++;
        }
        executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    final AllPagesQueryResponseWrapper allPages = client.getAllPages(pageSize, from);

                    List<AllPagesPage> responsePageList = allPages.getQueryResponse().getPages();
                    for (AllPagesPage page : responsePageList) {
                        if (page.getId() == null) {
                            logger.warn("Unexpected null id.", page);
                            continue;
                        }
                        fetchPage(page.getId());
                    }
                    fetchPageGroup(allPages.getQueryContinue().getAllPages().getContinueTitle());
                } catch ( RejectedExecutionException ex ) {
                    logger.warn("Cannot prefetch.", ex);
                } catch ( Exception ex ) {
                    logger.error("Error while fetching page group.", ex);
                    throw new IllegalStateException("Error while fetching page group.", ex);
                } finally {
                    synchronized (lock) {
                        pendingTasksCount--;
                    }
                }
                return null;
            }
        });
    }

    private void fetchPage(final long id) {
        synchronized (lock) {
            pendingTasksCount++;
        }

        executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    Page page = null;
                    RevisionsQueryResponseWrapper revisions = client.getRevisions(id);
                    RevisionsQueryPage responsePage = revisions.getQueryResponse().getPages().get(id);
                    if (   responsePage != null
                            && responsePage.getRevisions() != null
                            && responsePage.getRevisions().size() > 0) {
                        page = new Page();
                        page.setId( id );
                        page.setNamespace(responsePage.getNamespace());
                        page.setTitle( responsePage.getTitle() );
                        page.setWikiText(responsePage.getRevisions().get(0).getContent());

                        queue.add(page);
                    } else {
                        logger.warn("Unexpected format.", revisions);
                    }
                    return page;
                } catch ( Exception ex ) {
                    logger.error("Error while fetching page.", ex);
                    throw new IllegalStateException("Error while fetching page.", ex);
                } finally {
                    synchronized (lock) {
                        pendingTasksCount--;
                        lock.notifyAll();
                    }
                }
            }
        });
    }

    @Override
    public boolean hasNext() {
        synchronized (lock) {
            while ( queue.peek() == null ) {
                if ( pendingTasksCount == 0 ) {
                    return false;
                }
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new IllegalStateException("Fetching data was interrupted.", e);
                }
            }
            return true;
        }
    }

    @Override
    public Page next() {
        return queue.poll();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("PageIterator.remove is not supported.");
    }


}
