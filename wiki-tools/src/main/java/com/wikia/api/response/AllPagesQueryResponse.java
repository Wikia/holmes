package com.wikia.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 22:04
 */

public class AllPagesQueryResponse {
    @SerializedName("allpages")
    private List<AllPagesPage> pages;

    public List<AllPagesPage> getPages() {
        return pages;
    }

    public void setPages(List<AllPagesPage> pages) {
        this.pages = pages;
    }
}
