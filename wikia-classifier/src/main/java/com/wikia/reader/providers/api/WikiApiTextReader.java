package com.wikia.reader.providers.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wikia.reader.providers.WikiTextReader;
import com.wikia.reader.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.nio.client.HttpAsyncClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 14:45
 */
public class WikiApiTextReader implements WikiTextReader {
    private final static Logger logger = Logger.getLogger(WikiApiTextReader.class.toString());
    private final String apiUrl;

    public WikiApiTextReader(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public void getWikiText(String title, final FutureCallback<String> callback) throws IOException {
        final HttpAsyncClient client = HttpUtils.getCachedAsyncClient();
        client.start();
        final HttpGet request = new HttpGet(buildGetWikitextUri(title));
        client.execute(request, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {

                String string = null;
                try {
                    logger.log(Level.FINE, "GET " + request.getURI());
                    string = HttpUtils.readAll(httpResponse.getEntity());
                    String text = extractWikitext(string);
                    callback.completed(text);
                } catch (IOException e) {
                    callback.failed(e);
                } finally {
                    shutdownClient(client);
                }
            }

            @Override
            public void failed(Exception e) {
                logger.log(Level.SEVERE, "GET " + request.getURI() + " failed.", e);
                callback.failed(e);
                shutdownClient(client);
            }

            @Override
            public void cancelled() {
                logger.warning("GET " + request.getURI() + " cancelled.");
                callback.cancelled();
                shutdownClient(client);
            }
        });
    }

    private void shutdownClient(HttpAsyncClient client) {
        try {
            client.shutdown();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Shutdown Interupted.");
        }
    }

    protected URI buildGetWikitextUri(String title) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
            String url = apiUrl + "/api.php?format=json&action=query&titles=" + title + "&prop=revisions&rvprop=content";
            return URI.create(url);
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, "Unsupported encoding.", ex);
            throw new UnsupportedOperationException(ex);
        }
    }

    private String extractWikitext(String content) {
        JsonElement jsonElement = new JsonParser().parse(content);
        JsonObject pages = jsonElement.getAsJsonObject().get("query")
                .getAsJsonObject().get("pages")
                .getAsJsonObject();

        if(pages.entrySet().size() > 0) {
            if(pages.entrySet().size() > 1) {
                logger.warning("More than one page in response");
            }
            for(Map.Entry<String, JsonElement> entry: pages.entrySet()) {
                JsonArray revisions = entry.getValue()
                        .getAsJsonObject().get("revisions")
                        .getAsJsonArray();
                if(revisions.size() > 1) {
                    logger.warning("More than one revision in response");
                }
                return revisions.get(0).getAsJsonObject()
                        //.toString();
                        .get("*").getAsString();
            }
        }
        return "";
    }


}
