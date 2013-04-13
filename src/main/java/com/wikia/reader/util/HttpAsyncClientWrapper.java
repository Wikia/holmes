package com.wikia.reader.util;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.conn.ClientAsyncConnectionManager;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.nio.protocol.HttpAsyncResponseConsumer;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.nio.reactor.IOReactorStatus;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 17:02
 */
public class HttpAsyncClientWrapper implements HttpAsyncClient {
    private static Logger logger = Logger.getLogger(HttpAsyncClientWrapper.class.toString());
    private HttpAsyncClientManager manager;

    public HttpAsyncClientWrapper(HttpAsyncClientManager manager) {
        this.manager = manager;
    }

    public HttpAsyncClient getHttpAsyncClient() {
        if( manager.getClient() == null ) throw new IllegalStateException();
        return manager.getClient();
    }

    public void start() {
        try {
            manager.acquire();
        } catch (IOReactorException e) {
            logger.log(Level.SEVERE,"Cannot acquire HttpAsyncClient.", e);
        }
    }

    public IOReactorStatus getStatus() {
        return getHttpAsyncClient().getStatus();
    }

    public HttpParams getParams() {
        return getHttpAsyncClient().getParams();
    }

    public void shutdown() throws InterruptedException {
        manager.release();
    }

    public Future<HttpResponse> execute(HttpUriRequest httpUriRequest, FutureCallback<HttpResponse> httpResponseFutureCallback) {
        return getHttpAsyncClient().execute(httpUriRequest, httpResponseFutureCallback);
    }

    public ClientAsyncConnectionManager getConnectionManager() {
        return getHttpAsyncClient().getConnectionManager();
    }

    public <T> Future<T> execute(HttpAsyncRequestProducer httpAsyncRequestProducer, HttpAsyncResponseConsumer<T> tHttpAsyncResponseConsumer, FutureCallback<T> tFutureCallback) {
        return getHttpAsyncClient().execute(httpAsyncRequestProducer, tHttpAsyncResponseConsumer, tFutureCallback);
    }

    public <T> Future<T> execute(HttpAsyncRequestProducer httpAsyncRequestProducer, HttpAsyncResponseConsumer<T> tHttpAsyncResponseConsumer, HttpContext httpContext, FutureCallback<T> tFutureCallback) {
        return getHttpAsyncClient().execute(httpAsyncRequestProducer, tHttpAsyncResponseConsumer, httpContext, tFutureCallback);
    }

    public Future<HttpResponse> execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext, FutureCallback<HttpResponse> httpResponseFutureCallback) {
        return getHttpAsyncClient().execute(httpHost, httpRequest, httpContext, httpResponseFutureCallback);
    }

    public Future<HttpResponse> execute(HttpUriRequest httpUriRequest, HttpContext httpContext, FutureCallback<HttpResponse> httpResponseFutureCallback) {
        return getHttpAsyncClient().execute(httpUriRequest, httpContext, httpResponseFutureCallback);
    }

    public Future<HttpResponse> execute(HttpHost httpHost, HttpRequest httpRequest, FutureCallback<HttpResponse> httpResponseFutureCallback) {
        return getHttpAsyncClient().execute(httpHost, httpRequest, httpResponseFutureCallback);
    }
}
