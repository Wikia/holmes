package com.wikia.service.strategy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class WikiUrlStrategyImpl implements WikiUrlStrategy {
    private static Logger logger = LoggerFactory.getLogger(WikiUrlStrategyImpl.class);
    private static final Pattern pattern = Pattern.compile("^http:/[a-z0-9]", Pattern.CASE_INSENSITIVE);

    @Override
    public URL getUrl(String wikiName) throws UnkwnownWikiException {
        String originalName = wikiName;
        try {
            URL url;
            if( !wikiName.contains(".") ) {
                url = new URL("http://" +  wikiName + ".wikia.com");
            } else {
                if ( !wikiName.startsWith("http:/") ) {
                    wikiName = "http://" + wikiName;
                } else {
                    if( pattern.matcher(wikiName).find() ) {
                        wikiName = wikiName.replaceFirst("/", "//");
                    }
                }
                url = new URL(wikiName);
            }
            if("".equals(url.getPath())) {
                url = new URL(url, "/");
            }
            logger.debug(String.format("Interpret %s as %s", originalName, url.toString()));
            return url;
        } catch (MalformedURLException ex) {
            throw new UnkwnownWikiException("Cannot interpret supplied wiki name.", ex);
        }
    }
}
