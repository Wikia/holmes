package com.wikia.api.util.prefetch;/**
 * Author: Artur Dwornik
 * Date: 09.06.13
 * Time: 22:31
 */

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

/**
 * Tool for fetching executing concurrent fetch operations.
 * Every task can result in some number of results and some number of new tasks to execute.
 * Iterator will end when there will be no new tasks to execute
 * @param <T>
 */
public class PrefetchingIterator<T> implements Iterator<T> {
    private static Logger logger = LoggerFactory.getLogger(PrefetchingIterator.class);
    private final ListeningExecutorService executorService;
    private final Queue<T> resultsQueue = new ArrayDeque<>();
    private final Queue<PrefetchQueueTask<T>> taskQueue = new ArrayDeque<>();
    private long submittedTasks = 0;
    private long expectedPrefetchLimit = 16;
    private boolean interrupted = false;

    public PrefetchingIterator(ListeningExecutorService executorService, PrefetchQueueTask<T> initialTask) {
        this.executorService = executorService;
        addTask(initialTask);
    }

    protected synchronized void addTask(PrefetchQueueTask<T> prefetchQueueTask) {
        taskQueue.add(prefetchQueueTask);
        relax();
    }

    protected synchronized void relax() {
        // try estimate results and submittedTasks
        if( resultsQueue.size() + submittedTasks < expectedPrefetchLimit ) {
            PrefetchQueueTask<T> task = taskQueue.poll();
            if( task != null ) {
                sendTaskToExecutor( task );
            }
        }
    }

    protected synchronized void sendTaskToExecutor(PrefetchQueueTask<T> prefetchQueueTask) {
        submittedTasks++;
        final Object monitor = this;
        final ListenableFuture<PrefetchQueueTaskResponse<T>> response
                = executorService.submit(prefetchQueueTask);
        response.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    PrefetchQueueTaskResponse<T> prefetchQueueTaskResponse = response.get();
                    for( PrefetchQueueTask<T> task: prefetchQueueTaskResponse.getNewTasks() ) {
                        addTask(task);
                    }
                    addResults(prefetchQueueTaskResponse);
                } catch (InterruptedException e) {
                    interrupted = true;
                    logger.warn("Execution was interrupted.", e);
                } catch (ExecutionException e) {
                    logger.warn("Task execution error.", e);
                } catch (Throwable e) {
                    interrupted = true;
                    logger.error("Unexpected error.", e);
                    throw new IllegalStateException("Unexpected error.", e);
                } finally {
                    synchronized (monitor) {
                        submittedTasks--;
                        monitor.notifyAll();
                    }
                }
            }
        }, MoreExecutors.sameThreadExecutor());
    }

    private synchronized void addResults(PrefetchQueueTaskResponse<T> prefetchQueueTaskResponse) {
        resultsQueue.addAll(prefetchQueueTaskResponse.getResults());
        notifyAll();
    }

    @Override
    public synchronized boolean hasNext() {
        if( interrupted ) { return false; }
        while( (resultsQueue.isEmpty() && (submittedTasks != 0 || !taskQueue.isEmpty())) ) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.warn("Execution was interrupted.");
                interrupted = true;
                return false;
            }
        }
        return !resultsQueue.isEmpty();
    }

    @Override
    public synchronized T next() {
        if( hasNext() ) {
            T element = resultsQueue.poll();
            relax();
            return element;
        } else {
            throw new IllegalStateException("hasNext is false, next was executed.");
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
