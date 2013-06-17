package com.wikia.api.json;/**
 * Author: Artur Dwornik
 * Date: 03.06.13
 * Time: 23:49
 */

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.istack.internal.NotNull;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class JsonClientImpl implements JsonClient {
    private static Logger logger = LoggerFactory.getLogger(JsonClientImpl.class);

    public JsonElement getJsonElement(URI url) throws IOException {
        logger.info("GET: " + url);
        HttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        String response = httpClient.execute(httpGet, new BasicResponseHandler());
        return parseJson(response);
    }

    protected HttpClient getHttpClient() {
        return new DefaultHttpClient();
    }

    @Override
    public <T> T get(URI url, Class<T> tClass) throws IOException {
        return new Gson().fromJson(
                getJsonElement(url),
                tClass
        );
    }

    protected JsonElement parseJson(@NotNull String json) {
        if( json == null ) {
            throw new IllegalArgumentException("json parameter cannot be null.");
        }
        return new JsonParser().parse(json);
    }
}
