package com.wikia.api.client.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author: Artur Dwornik
 * Date: 29.06.13
 * Time: 17:37
 */

public class LinksLink implements Serializable {
    private static final long serialVersionUID = -3998065024988432594L;
    private String title;
    @SerializedName("ns")
    private Long namespace;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getNamespace() {
        return namespace;
    }

    public void setNamespace(Long namespace) {
        this.namespace = namespace;
    }
}

/*
{
    "ns": 0,
    "title": "A priori and a posteriori"
}
*/