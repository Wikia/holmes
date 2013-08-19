package com.wikia.api.client;/**
 * Author: Artur Dwornik
 * Date: 03.06.13
 * Time: 23:39
 */

import com.wikia.api.client.response.LinksResponseWrapper;
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
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ClientImpl.class);
    private final JsonClient jsonClient;
    private URL wikiApiRoot;

    public ClientImpl(@Nonnull JsonClient jsonClient, @Nonnull URL wikiApiRoot) {
        this.jsonClient = jsonClient;
        this.wikiApiRoot = wikiApiRoot;
    }

    @Override
    public RevisionsQueryResponseWrapper getRevisions(long pageId) throws IOException {
        URI query = URI.create(String.format("%s?action=query&prop=revisions&format=json&rvprop=content&rvlimit=1&pageids=%d&redirects=", wikiApiRoot.toString(), pageId));
        RevisionsQueryResponseWrapper revisionsQueryResponseWrapper = jsonClient.get(query, RevisionsQueryResponseWrapper.class);
        verifyRevisionsQueryResponseWrapper(revisionsQueryResponseWrapper);
        return revisionsQueryResponseWrapper;
    }

    @Override
    public RevisionsQueryResponseWrapper getRevisions(String title) throws IOException {
        if( title == null ) {
            throw new IllegalArgumentException("Title argument cannot be null.");
        }
        title = URLEncoder.encode(title, "UTF8");
        URI query = URI.create(String.format("%s?action=query&prop=revisions&format=json&rvprop=content&rvlimit=1&titles=%s&redirects=", wikiApiRoot.toString(), title));
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

    @Override
    public LinksResponseWrapper getLinks(long pageId, String continueFrom) throws IOException {
        String urlString = String.format("%s?action=query&prop=links&format=json&pllimit=500&pageids=%d&redirects=", wikiApiRoot.toString(), pageId);
        if( continueFrom != null ) {
            urlString += "&apfrom=" + URLEncoder.encode(continueFrom, "UTF-8");
        }
        URI query = URI.create(urlString);
        LinksResponseWrapper linksResponseWrapper = jsonClient.get(query, LinksResponseWrapper.class);
        if( linksResponseWrapper.getQueryResponse() == null ) {
            throw new IOException("Unexpected response from server");
        }
        return linksResponseWrapper;
    }

}
