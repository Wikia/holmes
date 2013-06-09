package com.wikia.api.response;/**
 * Author: Artur Dwornik
 * Date: 05.06.13
 * Time: 20:34
 */

import com.google.gson.annotations.SerializedName;

public class Revision {

    @SerializedName("*")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
