package com.wikia.reader.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 15:19
 */
public class AsyncQueues {
    private static Logger logger = Logger.getLogger(AsyncQueues.class.toString());

    public static <T> Iterator<T> synchronize(AsyncQueue<T> queue) {
        return new AsyncQueueIterator<T>(queue);
    }

    public static <T> List<T> toList(AsyncQueue<T> queue) {
        Iterator<T> iterator = synchronize(queue);
        List<T> list = new ArrayList<>();
        while( iterator.hasNext() ) {
            list.add(iterator.next());
        }
        return list;
    }

    public static <T> void pollAll(AsyncQueue<T> queue, AsyncQueueListener<T> listener) {
        new AsyncQueuePoller<T>(queue, listener);
    }

    public static <T> AsyncQueue<T> where(final AsyncQueue<T> queue, final Predicate<T> predicate) {
        final BasicAsyncQueue<T> resultQueue = new BasicAsyncQueue<T>();
        pollAll(queue, new AsyncQueueListener<T>() {
            @Override
            public void receive(T element) {
                if(predicate.evaluate(element)) {
                    resultQueue.pushOne(element);
                }
            }

            @Override
            public void close() {
                resultQueue.close();
            }
        });
        return resultQueue;
    }

}
