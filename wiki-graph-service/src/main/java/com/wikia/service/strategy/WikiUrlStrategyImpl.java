package com.wikia.service.strategy;
/**
 * Author: Artur Dwornik
 * Date: 23.06.13
 * Time: 14:47
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class WikiUrlStrategyImpl implements WikiUrlStrategy {
    private static Logger logger = LoggerFactory.getLogger(WikiUrlStrategyImpl.class);

    @Override
    public URL getUrl(String wikiName) throws UnkwnownWikiException {
        String originalName = wikiName;
        try {
            URL url;
            if( !wikiName.contains(".") ) {
                url = new URL("http://" +  wikiName + ".wikia.com");
            } else {
                if ( !wikiName.startsWith("http://") ) {
                    wikiName = "http://" + wikiName;
                }
                url = new URL(wikiName);
            }
            logger.debug(String.format("Interpret %s as %s", originalName, url.toString()));
            return url;
        } catch (MalformedURLException ex) {
            throw new UnkwnownWikiException("Cannot interpret supplied wiki name.", ex);
        }
    }
}
