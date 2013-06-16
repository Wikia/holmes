package com.wikia.api.client;/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 15:22
 */

import com.wikia.api.json.JsonClientImpl;
import com.wikia.api.service.PageService;
import com.wikia.api.service.PageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;

public class PagesServiceIntegrationTestsSimple extends PagesServiceIntegrationTestsBase {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(PagesServiceIntegrationTestsSimple.class);

    @Override
    protected PageService createPageService(String wikiApi) throws MalformedURLException {
        return new PageServiceImpl( new ClientImpl(new JsonClientImpl(), URI.create(wikiApi).toURL()) );
    }
}
