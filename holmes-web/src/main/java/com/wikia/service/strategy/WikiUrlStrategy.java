package com.wikia.service.strategy;

import java.net.URL;


public interface WikiUrlStrategy {
    URL getUrl(String wikiName) throws UnknownWikiException;
}
