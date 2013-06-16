package com.wikia.api.service;/**
 * Author: Artur Dwornik
 * Date: 11.06.13
 * Time: 15:21
 */

import com.google.common.base.Strings;
import com.wikia.api.client.ClientFactory;
import com.wikia.api.client.ClientFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PageServiceFactory {
    private static Logger logger = LoggerFactory.getLogger(PageServiceFactory.class);
    private ClientFactory clientFactory;

    public PageServiceFactory() {
        this(new ClientFactoryImpl());
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
        return new PageServiceImpl( clientFactory.get(apiRoot) );
    }
}
