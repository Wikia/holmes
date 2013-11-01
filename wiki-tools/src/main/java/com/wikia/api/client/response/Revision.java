package com.wikia.api.client.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Revision  implements Serializable {
    private static final long serialVersionUID = 4165616528273077702L;
    @SerializedName("*")
    private String content;

    public String getContent() {
        return content;
    }

    @SuppressWarnings("unused")
    public void setContent(String content) {
        this.content = content;
    }
}
