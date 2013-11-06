package com.wikia.api.client.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



public class AllPagesAPContinue implements Serializable {
    private static final long serialVersionUID = 6142999253998257754L;
    @SerializedName("apfrom")
    private String apFrom;
    @SerializedName("apcontinue")
    private String apContinue;

    public String getContinueTitle() {
        if ( getApFrom() == null || getApFrom().isEmpty() ) {
            return getApContinue();
        } else {
            return getApFrom();
        }
    }

    public String getApFrom() {
        return apFrom;
    }

    public void setApFrom(String apFrom) {
        this.apFrom = apFrom;
    }

    public String getApContinue() {
        return apContinue;
    }

    public void setApContinue(String apContinue) {
        this.apContinue = apContinue;
    }
}
