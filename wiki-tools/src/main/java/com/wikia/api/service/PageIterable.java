package com.wikia.api.service;/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 17:11
 */

import com.google.common.util.concurrent.MoreExecutors;
import com.wikia.api.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class PageIterable implements Iterable<Page> {
    private static Logger logger = LoggerFactory.getLogger(PageIterable.class);

    public PageIterable(Client client) {
        this.client = client;
    }

    private Client client;

    @Override
    public Iterator<Page> iterator() {
        PageIterator iterator =
                new PageIterator(client, MoreExecutors.listeningDecorator(
                        MoreExecutors.getExitingExecutorService(
                                (ThreadPoolExecutor) Executors.newFixedThreadPool(8))));
        iterator.start();
        return iterator;
    }
}
