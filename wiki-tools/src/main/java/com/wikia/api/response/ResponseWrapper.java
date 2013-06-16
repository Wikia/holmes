package com.wikia.api.response;/**
 * Author: Artur Dwornik
 * Date: 04.06.13
 * Time: 20:54
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

abstract public class ResponseWrapper<T> {
    @SerializedName("query")
    private T queryResponse;

    public T getQueryResponse() {
        return queryResponse;
    }

    public void setQueryResponse(T queryResponse) {
        this.queryResponse = queryResponse;
    }
}

