package com.wikia.api.util.prefetch;

import java.util.concurrent.Callable;


public interface PrefetchQueueTask<T> extends Callable<PrefetchQueueTaskResponse<T>> {
}
