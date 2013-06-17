package com.wikia.reader.util;

import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 17:24
 */
public interface HttpAsyncClientManager {
    void acquire() throws IOReactorException;

    void release() throws InterruptedException;

    HttpAsyncClient getClient();
}
