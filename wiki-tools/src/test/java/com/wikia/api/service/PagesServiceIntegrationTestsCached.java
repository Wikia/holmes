package com.wikia.api.service;

import java.io.IOException;
import java.net.URI;

public class PagesServiceIntegrationTestsCached extends PagesServiceIntegrationTestsBase {

    @Override
    protected PageService createPageService(String wikiApi) throws IOException {
        return new PageServiceFactory().get(URI.create(wikiApi).toURL());
    }
}
