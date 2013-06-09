package com.wikia.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 22:04
 */

public class AllPagesQueryResponseWrapper extends ResponseWrapper<AllPagesQueryResponse> {
    @SerializedName("query-continue")
    private AllPagesQueryContinue queryContinue;

    public AllPagesQueryContinue getQueryContinue() {
        return queryContinue;
    }

    public void setQueryContinue(AllPagesQueryContinue queryContinue) {
        this.queryContinue = queryContinue;
    }
}
