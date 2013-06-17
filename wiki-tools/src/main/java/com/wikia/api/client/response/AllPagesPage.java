package com.wikia.api.client.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 22:17
 */

public class AllPagesPage  implements Serializable {
    private static final long serialVersionUID = -3138162125622564487L;
    @SerializedName("pageid")
    private Long id;
    private String title;
    @SerializedName("ns")
    private Long namespace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
