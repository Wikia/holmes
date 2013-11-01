package com.wikia.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class PagesServiceIntegrationTestsSimple extends PagesServiceIntegrationTestsBase {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(PagesServiceIntegrationTestsSimple.class);

    @Override
    protected PageService createPageService(String wikiApi) throws IOException {
        return new PageServiceFactory().get(new URL(wikiApi));
    }
}
