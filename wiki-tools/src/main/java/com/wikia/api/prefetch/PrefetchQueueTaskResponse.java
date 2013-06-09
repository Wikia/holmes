package com.wikia.api.prefetch;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Artur Dwornik
 * Date: 09.06.13
 * Time: 22:00
 */

public class PrefetchQueueTaskResponse<T> {
    private List<PrefetchQueueTask> moreTasks = new ArrayList<>();
    private List<T> results = new ArrayList<>();

    public List<PrefetchQueueTask> getMoreTasks() {
        return moreTasks;
    }

    public void setMoreTasks(List<PrefetchQueueTask> moreTasks) {
        this.moreTasks = moreTasks;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
