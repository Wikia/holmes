package com.wikia.api.client;/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 15:25
 */

import com.wikia.api.service.PageService;
import com.wikia.api.service.PageServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class PagesServiceIntegrationTestsBaseCached extends PagesServiceIntegrationTestsBase {
    private static Logger logger = LoggerFactory.getLogger(PagesServiceIntegrationTestsBaseCached.class);

    @Override
    protected PageService createPageService(String wikiApi) throws IOException {
        return new PageServiceFactory().get(URI.create(wikiApi).toURL());
    }
}
