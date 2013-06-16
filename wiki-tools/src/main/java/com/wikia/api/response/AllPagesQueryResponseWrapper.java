package com.wikia.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 22:04
 */

public class AllPagesQueryResponseWrapper  extends ResponseWrapper<AllPagesQueryResponse>  implements Serializable {
    private static final long serialVersionUID = 2556896394516290085L;
    @SerializedName("query-continue")
    private AllPagesQueryContinue queryContinue;

    public AllPagesQueryContinue getQueryContinue() {
        return queryContinue;
    }

    public void setQueryContinue(AllPagesQueryContinue queryContinue) {
        this.queryContinue = queryContinue;
    }
}
