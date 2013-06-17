package com.wikia.reader.util;

import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 17:17
 */
public class DefaultHttpAsyncClientManager implements HttpAsyncClientManager {
    private static Logger logger = Logger.getLogger(DefaultHttpAsyncClientManager.class.toString());
    private HttpAsyncClient client = null;
    private long count = 0;

    @Override
    public synchronized void acquire() throws IOReactorException {
        if( count==0 ) {
            client = new DefaultHttpAsyncClient();
            client.start();
        }
        count++;
    }

    @Override
    public synchronized void release() throws InterruptedException {
        count--;
        if( count == 0 ) {
            final HttpAsyncClient oldClient = client;
            // prevent joining from dispatcher thread. DIRTY !!
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        oldClient.shutdown();
                    } catch (InterruptedException e) {
                        logger.log(Level.SEVERE, "Error shutting down.", e);
                    }
                }
            }).start();
            client = null;
        }
    }

    @Override
    public synchronized HttpAsyncClient getClient() {
        return client;
    }
}
