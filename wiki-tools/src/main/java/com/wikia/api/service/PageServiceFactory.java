package com.wikia.api.service;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.wikia.api.client.Client;
import com.wikia.api.client.ClientFactory;
import com.wikia.api.client.ClientFactoryImpl;
import com.wikia.api.model.builder.LazyPageBuilderImpl;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;

public class PageServiceFactory {
    private static Logger logger = LoggerFactory.getLogger(PageServiceFactory.class);
    private ClientFactory clientFactory;
    private ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(8));

    public PageServiceFactory() {
        PoolingClientConnectionManager clientConnectionManager = new PoolingClientConnectionManager();
        clientConnectionManager.setDefaultMaxPerRoute(10);
        clientConnectionManager.setMaxTotal(100);
        this.clientFactory = (new ClientFactoryImpl(clientConnectionManager));
    }

    public PageServiceFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public PageService get(URL apiRoot) throws IOException {
        logger.debug("Get PageServiceFactory " + apiRoot);
        if( Strings.isNullOrEmpty(apiRoot.getFile()) || "/".equals( apiRoot.getFile() ) ) {
            // transform http://cod.wikia.com/ to http://cod.wikia.com/api.php
            // TODO verify if api path is correct to suppoert /w/api.php
            try {
                logger.debug("before:"  + apiRoot);
                apiRoot = new URL( apiRoot, "api.php" );
                logger.debug("before:"  + apiRoot);
            } catch (MalformedURLException e) {
                logger.warn("Unexpected error while modifying url.", e);
            }
        }
        Client client = clientFactory.get(apiRoot);
        return new PageServiceImpl( client
                , new LazyPageBuilderImpl(client, executorService)
                , executorService);
    }
}
