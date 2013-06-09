package com.wikia.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 23:26
 */

public class AllPagesQueryContinue {
    @SerializedName("allpages")
    private AllPagesAPContinue allPages;

    public AllPagesAPContinue getAllPages() {
        return allPages;
    }

    public void setAllPages(AllPagesAPContinue allPages) {
        this.allPages = allPages;
    }
}
