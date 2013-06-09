package com.wikia.api.util;/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 00:46
 */

import com.google.common.base.Function;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class Futures {
    private static Logger logger = LoggerFactory.getLogger(Futures.class);

    public static <T,R> ListenableFuture<R> chain( final ListenableFuture<T> input, final Function<T,R> transformFunction) {
        final SettableFuture<R> future = SettableFuture.create();
        input.addListener( new Runnable() {
            @Override
            public void run() {
                try {
                    R value = transformFunction.apply(input.get());
                    future.set(value);
                } catch (InterruptedException e) {
                    future.setException(e);
                } catch (ExecutionException e) {
                    future.setException(e);
                }
            }
        }, MoreExecutors.sameThreadExecutor());
        return future;
    }
}
