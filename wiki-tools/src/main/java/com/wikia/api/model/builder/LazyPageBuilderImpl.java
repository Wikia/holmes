package com.wikia.api.model.builder;
/**
 * Author: Artur Dwornik
 * Date: 30.06.13
 * Time: 00:04
 */

import com.google.common.util.concurrent.ListeningExecutorService;
import com.wikia.api.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LazyPageBuilderImpl implements LazyPageBuilder {
    private static Logger logger = LoggerFactory.getLogger(LazyPageBuilderImpl.class);
    private final Client client;
    private final ListeningExecutorService listeningExecutorService;

    public LazyPageBuilderImpl(Client client, ListeningExecutorService listeningExecutorService) {
        this.client = client;
        this.listeningExecutorService = listeningExecutorService;
    }

    @Override
    public LazyPageBuilderContext byId(long pageId) {
        LazyPageBuilderContextImpl context = new LazyPageBuilderContextImpl(client, listeningExecutorService, this);
        context.setId(pageId);
        return context;
    }

    @Override
    public LazyPageBuilderContext byTitle(String title) {
        LazyPageBuilderContextImpl context = new LazyPageBuilderContextImpl(client, listeningExecutorService, this);
        context.setTitle(title);
        return context;
    }
}
