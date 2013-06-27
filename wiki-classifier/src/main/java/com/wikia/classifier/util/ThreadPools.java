package com.wikia.classifier.util;/**
 * Author: Artur Dwornik
 * Date: 08.04.13
 * Time: 22:20
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPools {
    private static Logger logger = LoggerFactory.getLogger(ThreadPools.class);
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    public static ExecutorService get() {
        return executorService;
    }
}
