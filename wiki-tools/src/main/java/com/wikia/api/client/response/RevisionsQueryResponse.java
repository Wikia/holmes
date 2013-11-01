package com.wikia.api.client.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RevisionsQueryResponse  implements Serializable {

    private static final long serialVersionUID = 8359512122886975310L;
    @SerializedName("query")
    private List<Redirect> redirects;
    private Map<Long, RevisionsQueryPage> pages;

    public List<Redirect> getRedirects() {
        return redirects;
    }

    public void setRedirects(List<Redirect> redirects) {
        this.redirects = redirects;
    }

    public Map<Long, RevisionsQueryPage> getPages() {
        return pages;
    }

    public void setPages(Map<Long, RevisionsQueryPage> pages) {
        this.pages = pages;
    }
}
