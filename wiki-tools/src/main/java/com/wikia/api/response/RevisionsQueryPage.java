package com.wikia.api.response;/**
 * Author: Artur Dwornik
 * Date: 04.06.13
 * Time: 21:16
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RevisionsQueryPage  implements Serializable {

    private static final long serialVersionUID = 6782678269755919271L;
    @SerializedName("pageid")
    private Long pageId;
    @SerializedName("ns")
    private Long namespace;
    private String title;
    private List<Revision> revisions;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getNamespace() {
        return namespace;
    }

    public void setNamespace(Long namespace) {
        this.namespace = namespace;
    }

    public String getTitle() {
        return title;
    }

    public List<Revision> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }

    public void setTitle(String title) {
        this.title = title;

    }
}
