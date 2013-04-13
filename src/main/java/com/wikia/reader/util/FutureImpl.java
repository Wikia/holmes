package com.wikia.reader.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 31.03.13
 * Time: 17:58
 */
public class FutureImpl<T> implements Future<T> {
    private static Logger logger = Logger.getLogger(FutureImpl.class.toString());
    private boolean done = false;
    private T result = null;
    private Exception exception = null;
    private boolean cancelled = false;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public synchronized boolean isDone() {
        return done;
    }

    @Override
    public synchronized T get() throws InterruptedException, ExecutionException {
        while( !done && exception != null && !cancelled ) {
            wait();
        }
        if( exception != null ) throw new ExecutionException(exception);
        if( cancelled ) throw new InterruptedException();
        return result;
    }

    @Override
    public synchronized T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if( exception != null ) throw new ExecutionException(exception);
        if( cancelled ) throw new InterruptedException();
        if( !done ) wait(unit.toMillis(timeout));
        if( exception != null ) throw new ExecutionException(exception);
        if( cancelled ) throw new InterruptedException();
        if( !done ) throw new TimeoutException("Get timed out.");
        return result;
    }

    public synchronized void done(T result) {
        this.result = result;
        this.done = true;
        notifyAll();

    }

    public synchronized void failed(Exception e) {
        this.exception = e;
        notifyAll();
    }

    public synchronized void cancelled() {
        this.cancelled = true;
        notifyAll();
    }
}
