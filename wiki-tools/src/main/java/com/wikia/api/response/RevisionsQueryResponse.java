package com.wikia.api.response;/**
 * Author: Artur Dwornik
 * Date: 04.06.13
 * Time: 21:13
 */

import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RevisionsQueryResponse  implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(RevisionsQueryResponse.class);

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
