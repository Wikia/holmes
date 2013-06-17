package com.wikia.api.client;/**
 * Author: Artur Dwornik
 * Date: 03.06.13
 * Time: 23:39
 */

import com.wikia.api.json.JsonClient;
import com.wikia.api.client.response.AllPagesQueryResponseWrapper;
import com.wikia.api.client.response.RevisionsQueryResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class ClientImpl implements Client {
    private static Logger logger = LoggerFactory.getLogger(ClientImpl.class);
    private final JsonClient jsonClient;
    private URL wikiApiRoot;

    public ClientImpl(@Nonnull JsonClient jsonClient, @Nonnull URL wikiApiRoot) {
        this.jsonClient = jsonClient;
        this.wikiApiRoot = wikiApiRoot;
    }

    @Override
    public RevisionsQueryResponseWrapper getRevisions(long pageId) throws IOException {
        URI query = URI.create(wikiApiRoot.toString() + "?action=query&prop=revisions&format=json&rvprop=content&rvlimit=1&pageids=" + pageId );
        RevisionsQueryResponseWrapper revisionsQueryResponseWrapper = jsonClient.get(query, RevisionsQueryResponseWrapper.class);
        verifyRevisionsQueryResponseWrapper(revisionsQueryResponseWrapper);
        return revisionsQueryResponseWrapper;
    }

    @Override
    public RevisionsQueryResponseWrapper getRevisions(String title) throws IOException {
        title = URLEncoder.encode(title, "UTF8");
        URI query = URI.create(wikiApiRoot.toString() + "?action=query&prop=revisions&format=json&rvprop=content&rvlimit=1&titles=" + title );
        RevisionsQueryResponseWrapper revisionsQueryResponseWrapper = jsonClient.get(query, RevisionsQueryResponseWrapper.class);
        verifyRevisionsQueryResponseWrapper(revisionsQueryResponseWrapper);
        return revisionsQueryResponseWrapper;
    }

    /**
     * Verify if response is correct. Throw otherwise.
     * @param revisionsQueryResponseWrapper model to verify
     * @throws IOException
     */
    private void verifyRevisionsQueryResponseWrapper(RevisionsQueryResponseWrapper revisionsQueryResponseWrapper) throws IOException {
        if( revisionsQueryResponseWrapper == null
                || revisionsQueryResponseWrapper.getQueryResponse() == null
                || revisionsQueryResponseWrapper.getQueryResponse().getPages() == null ) {
            throw new IOException("Bad response format.");
        }
    }


    @Override
    public AllPagesQueryResponseWrapper getAllPages(long count, String apFrom) throws IOException {
        String urlString = wikiApiRoot.toString() + "?action=query&list=allpages&format=json&apfilterredir=nonredirects&aplimit=" + count + "&redirects=&converttitles=";
        if ( apFrom != null ) {
            urlString += "&apfrom=" + URLEncoder.encode(apFrom, "UTF-8");
        }
        URI query = URI.create(urlString);
        AllPagesQueryResponseWrapper allPagesQueryResponseWrapper = jsonClient.get(query, AllPagesQueryResponseWrapper.class);
        if ( allPagesQueryResponseWrapper == null
                || allPagesQueryResponseWrapper.getQueryResponse() == null
                || allPagesQueryResponseWrapper.getQueryResponse().getPages() == null ) {
            throw new IOException("Bad response format.");
        }
        return allPagesQueryResponseWrapper;
    }

}
