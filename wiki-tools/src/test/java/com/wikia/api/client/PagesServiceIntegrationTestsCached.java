package com.wikia.api.client;/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 15:25
 */

import com.wikia.api.service.PageService;
import com.wikia.api.service.PageServiceFactory;

import java.io.IOException;
import java.net.URI;

public class PagesServiceIntegrationTestsCached extends PagesServiceIntegrationTestsBase {

    @Override
    protected PageService createPageService(String wikiApi) throws IOException {
        return new PageServiceFactory().get(URI.create(wikiApi).toURL());
    }
}
