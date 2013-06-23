package com.wikia.service.strategy;

import java.net.URL;

/**
 * Author: Artur Dwornik
 * Date: 23.06.13
 * Time: 14:46
 */
public interface WikiUrlStrategy {
    URL getUrl(String wikiName) throws UnkwnownWikiException;
}
