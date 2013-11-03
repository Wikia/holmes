package com.wikia.api.client.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



public class AllPagesAPContinue implements Serializable {
    private static final long serialVersionUID = 6142999253998257754L;
    @SerializedName("apfrom")
    private String continueTitle;

    public String getContinueTitle() {
        return continueTitle;
    }

    public void setContinueTitle(String continueTitle) {
        this.continueTitle = continueTitle;
    }
}
