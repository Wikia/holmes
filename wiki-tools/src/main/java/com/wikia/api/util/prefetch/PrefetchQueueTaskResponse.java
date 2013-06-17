package com.wikia.api.util.prefetch;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Artur Dwornik
 * Date: 09.06.13
 * Time: 22:00
 */

/**
 * Represents PrefetchingIterator task result.
 * Every task can return any number of new tasks to execute and any number of results.
 * @param <T>
 */
public class PrefetchQueueTaskResponse<T> {
    private List<PrefetchQueueTask> newTasks = new ArrayList<>();
    private List<T> results = new ArrayList<>();

    public List<PrefetchQueueTask> getNewTasks() {
        return newTasks;
    }

    public void setNewTasks(List<PrefetchQueueTask> newTasks) {
        this.newTasks = newTasks;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
