package com.wikia.reader.providers.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wikia.reader.providers.WikiIndexReader;
import com.wikia.reader.util.AsyncQueue;
import com.wikia.reader.util.BasicAsyncQueue;
import com.wikia.reader.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.nio.client.HttpAsyncClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 13:11
 */
public class WikiApiIndexReader implements WikiIndexReader {
    private static Logger logger = Logger.getLogger(WikiApiIndexReader.class.toString());
    private final String apiUrl;
    private final long pagesPerIndex;
    private final Set<String> was = new HashSet<>();

    public WikiApiIndexReader(String apiUrl, long pagesPerIndex) {
        this.apiUrl = apiUrl;
        this.pagesPerIndex = pagesPerIndex;
    }

    @Override
    public AsyncQueue<String> getIndex() throws IOException {
        final BasicAsyncQueue<String> asyncQueue = new BasicAsyncQueue<>();

        final HttpAsyncClient client = HttpUtils.getCachedAsyncClient();
        client.start();
        fetchNextPageBanch(asyncQueue, client, null);
        return asyncQueue;
    }

    private void fetchNextPageBanch(final BasicAsyncQueue<String> asyncQueue, final HttpAsyncClient client, final String apFrom) {
        final HttpGet request = new HttpGet(buildGetIndexUri(apFrom));
        client.execute(request, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
                List<String> titles = new ArrayList<>();
                int count = 0;
                try {
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        logger.fine("GET " + request.getURI());
                        String string = HttpUtils.readAll(httpResponse.getEntity());
                        titles = getTitles(string);
                        logger.fine("Found " + titles.size() + " titles.");
                    } else {
                        logger.log(Level.SEVERE, "Cannot get api index (" + request.getURI() + ")");
                    }
                    for (String title : titles) {
                        if(was.add(title)) {
                            count ++;
                            asyncQueue.pushOne(title);
                        }
                    }
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error while fetching index data.");
                } finally {
                    if( count == 0 ) {
                        asyncQueue.close();
                        shutdownClient(client);
                    } else {
                        String last = titles.get(titles.size()-1);
                        fetchNextPageBanch(asyncQueue, client, last);
                    }
                }
            }

            @Override
            public void failed(Exception e) {
                logger.log(Level.SEVERE, "Error while fetching index data.", e);
                asyncQueue.close();
                shutdownClient(client);
            }

            @Override
            public void cancelled() {
                logger.warning("Index fetching cancelled");
                asyncQueue.close();
                shutdownClient(client);
            }
        });
    }

    protected URI buildGetIndexUri(String apFrom) {
        String url = apiUrl + "/api.php?action=query&list=allpages&format=json&aplimit=" + this.pagesPerIndex;
        if( apFrom!=null ) {
            try {
                url = url + "&apfrom=" + URLEncoder.encode(apFrom, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.log(Level.SEVERE, "Cannot encode String. Unsupported Encoding.", e);
                throw new IllegalStateException(e);
            }
        }
        return URI.create(url);
    }

    private List<String> getTitles(String string) {
        JsonElement jsonElement = new JsonParser().parse(string);
        JsonArray jsonArray = jsonElement.getAsJsonObject().get("query")
                .getAsJsonObject().get("allpages")
                .getAsJsonArray();
        List<String> titles = new ArrayList<>();
        for(JsonElement e: jsonArray) {
            String title = e.getAsJsonObject().get("title").getAsString();
            titles.add(title);
        }
        return titles;
    }

    private void shutdownClient(HttpAsyncClient client) {
        try {
            client.shutdown();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Shutdown Interupted.");
        }
    }
}
