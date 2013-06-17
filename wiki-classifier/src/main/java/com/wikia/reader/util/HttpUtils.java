package com.wikia.reader.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.cache.ResourceFactory;
import org.apache.http.impl.client.cache.BasicHttpCacheStorage;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.FileResourceFactory;
import org.apache.http.impl.client.cache.ehcache.EhcacheHttpCacheStorage;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.impl.client.cache.CachingHttpAsyncClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 13:10
 */
public class HttpUtils {
    private static HttpAsyncClientManager httpAsyncClientManager = new DefaultHttpAsyncClientManager();

    public static String readAll(HttpEntity httpEntity) throws IOException {
        StringWriter writer = new StringWriter();
        InputStream is = httpEntity.getContent();
        Header header = httpEntity.getContentEncoding();
        IOUtils.copy(is, writer, header == null ? "UTF-8" : header.getValue());
        String theString = writer.toString();
        return theString;
    }

    public static HttpAsyncClient getAsyncClient() {
        return new HttpAsyncClientWrapper(httpAsyncClientManager);
    }

    public static HttpAsyncClient getCachedAsyncClient() {
        CacheConfig config = new CacheConfig();
        BasicHttpCacheStorage storage = new BasicHttpCacheStorage(config);
        ResourceFactory resourceFactory =
                   new FileResourceFactory(new File(System.getProperty("java.io.tmpdir")));
        return new CachingHttpAsyncClient(getAsyncClient(), resourceFactory, storage, config);
    }
}
