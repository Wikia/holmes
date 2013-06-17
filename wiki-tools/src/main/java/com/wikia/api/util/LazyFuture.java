package com.wikia.api.util;
/**
 * Author: Artur Dwornik
 * Date: 17.06.13
 * Time: 23:31
 */

import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class LazyFuture<T> implements ListenableFuture<T> {
    private static Logger logger = LoggerFactory.getLogger(LazyFuture.class);
    private final Supplier<ListenableFuture<T>> listenableFutureFactory;
    private ListenableFuture<T> listenableFuture;

    public static <T> LazyFuture<T> create(final Callable<T> supplier, final ListeningExecutorService executorService) {
        return new LazyFuture<T>(new Supplier<ListenableFuture<T>>() {
            @Override
            public ListenableFuture<T> get() {
                return executorService.submit(supplier);
            }
        });
    }

    public static <T> LazyFuture<T> createNonLazy(final T value) {
        return create(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return value;
            }
        }, MoreExecutors.sameThreadExecutor());
    }

    public LazyFuture(Supplier<ListenableFuture<T>> listenableFutureFactory) {
        this.listenableFutureFactory = listenableFutureFactory;
    }

    public void startLoading() {
        getListenableFuture();
    }

    protected synchronized ListenableFuture<T> getListenableFuture() {
        if( listenableFuture == null ) {
            listenableFuture = listenableFutureFactory.get();
        }
        return listenableFuture;
    }

    @Override
    public void addListener(Runnable listener, Executor executor) {
        getListenableFuture().addListener(listener, executor);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return getListenableFuture().cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return getListenableFuture().isCancelled();
    }

    @Override
    public boolean isDone() {
        return getListenableFuture().isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return getListenableFuture().get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return getListenableFuture().get(timeout, unit);
    }

    public T falselySafeGet() {
        try {
            return get();
        } catch (InterruptedException e) {
            throw new IllegalStateException("Unexpected error.", e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("Unexpected error.", e);
        }
    }
}
