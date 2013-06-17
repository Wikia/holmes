package com.wikia.api.util.prefetch;

import java.util.concurrent.Callable;

/**
 * Author: Artur Dwornik
 * Date: 09.06.13
 * Time: 22:00
 */
public interface PrefetchQueueTask<T> extends Callable<PrefetchQueueTaskResponse<T>> {
}
