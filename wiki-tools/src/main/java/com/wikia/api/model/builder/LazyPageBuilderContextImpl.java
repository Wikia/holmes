package com.wikia.api.model.builder;
/**
 * Author: Artur Dwornik
 * Date: 30.06.13
 * Time: 00:11
 */

import com.google.common.util.concurrent.ListeningExecutorService;
import com.wikia.api.client.Client;
import com.wikia.api.client.response.LinksLink;
import com.wikia.api.client.response.LinksResponseWrapper;
import com.wikia.api.model.LazyPage;
import com.wikia.api.model.PageInfo;
import com.wikia.api.util.LazyFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class LazyPageBuilderContextImpl implements LazyPageBuilderContext {
    private static Logger logger = LoggerFactory.getLogger(LazyPageBuilderContextImpl.class);
    private Long id;
    private Long namespace;
    private String title;
    private String wikiText;
    private List<PageInfo> links;
    private final Client client;
    private final ListeningExecutorService executorService;
    private final LazyPageBuilder builder;

    public LazyPageBuilderContextImpl(Client client, ListeningExecutorService executorService, LazyPageBuilder pageBuilder) {
        if( client == null ) {
            throw new IllegalArgumentException("Client cannot be null.");
        }
        if( executorService == null ) {
            throw new IllegalArgumentException("ExecutorService cannot be null.");
        }
        if( pageBuilder == null ) {
            throw new IllegalArgumentException("PageBuilder cannot null.");
        }
        this.client = client;
        this.executorService = executorService;
        this.builder = pageBuilder;
    }

    @Override
    public LazyPageBuilderContext setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public LazyPageBuilderContext setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public LazyPageBuilderContext setNamespace(long ns) {
        this.namespace = ns;
        return this;
    }

    @Override
    public LazyPageBuilderContext setWikiText(String wikiText) {
        this.wikiText = wikiText;
        return this;
    }

    @Override
    public LazyPageBuilderContext setLinks(List<PageInfo> links) {
        this.links = links;
        return this;
    }

    @Override
    public LazyPage build() {
        final LazyPage page = new LazyPage();
        if( id == null ) {
            if( title == null ) {
                throw new IllegalStateException("Cannot create page. Needs id or title.");
            } else {
                page.setFutureId(LazyFuture.create(new Callable<Long>() {
                    @Override
                    public Long call() throws Exception {
                        return client.getRevisions(title).singlePageRevision()
                                .getPageId();
                    }
                }, executorService));
            }
        } else {
            page.setId(id);
        }
        if( title == null ) {
            // id not null check is already done
            assert(id != null);
            page.setFutureTitle(LazyFuture.create(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return client.getRevisions(id).singlePageRevision().getTitle();
                }
            }, executorService));
        } else {
            page.setFutureTitle(LazyFuture.create(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String newTitle = client.getRevisions(title).singlePageRevision().getTitle();
                    return newTitle;
                }
            }, executorService));
        }
        if( namespace == null ) {
            page.setFutureNamespace(LazyFuture.create(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    return client.getRevisions(page.getTitle()).singlePageRevision().getNamespace();
                    // We can optimise by getting id if id is available and title is not
                }
            }, executorService));
        } else {
            page.setNamespace(namespace);
        }
        if( wikiText == null ) {
            page.setFutureWikiText(LazyFuture.create(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return client.getRevisions(page.getTitle()).singlePageRevision()
                            .getRevisions().get(0).getContent();
                }
            }, executorService));
        } else {
            page.setWikiText(wikiText);
        }
        if( links == null ) {
            page.setFutureLinks(LazyFuture.create(new Callable<List<PageInfo>>() {
                @Override
                public List<PageInfo> call() throws Exception {
                    LinksResponseWrapper linksResponse = client.getLinks(page.getPageId(), null);
                    List<LinksLink> links = linksResponse.getQueryResponse().getPages().entrySet().iterator().next().getValue().getLinks();
                    List<PageInfo> linkedPages = new ArrayList<>();
                    for( LinksLink link: links ) {
                        LazyPage linkedPage = builder.byTitle(link.getTitle())
                                .setNamespace(link.getNamespace())
                                .build();
                        linkedPages.add(linkedPage);
                    }
                    return linkedPages;
                }
            }, executorService));
        } else {
            page.setLinks(links);
        }
        return page;
    }


}
