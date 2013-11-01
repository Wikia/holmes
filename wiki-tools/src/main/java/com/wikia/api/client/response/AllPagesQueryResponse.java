package com.wikia.api.client.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;



public class AllPagesQueryResponse  implements Serializable {
    private static final long serialVersionUID = -6876638129022603317L;
    @SerializedName("allpages")
    private List<AllPagesPage> pages;

    public List<AllPagesPage> getPages() {
        return pages;
    }

    public void setPages(List<AllPagesPage> pages) {
        this.pages = pages;
    }
}
