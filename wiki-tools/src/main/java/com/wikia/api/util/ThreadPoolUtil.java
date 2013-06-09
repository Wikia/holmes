package com.wikia.api.util;/**
 * Author: Artur Dwornik
 * Date: 05.06.13
 * Time: 20:53
 */

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class ThreadPoolUtil {
    private static Logger logger = LoggerFactory.getLogger(ThreadPoolUtil.class);
    private static ListeningExecutorService executorService;

    static {
        logger.info("Start thread pool.");
        executorService = MoreExecutors.listeningDecorator(
                Executors.newCachedThreadPool());
    }

    public static ListeningExecutorService getExecutorService() {
        return executorService;
    }
}
