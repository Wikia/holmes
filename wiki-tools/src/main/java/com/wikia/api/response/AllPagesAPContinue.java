package com.wikia.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 23:32
 */

public class AllPagesAPContinue implements Serializable {
    @SerializedName("apcontinue")
    private String continueTitle;

    public String getContinueTitle() {
        return continueTitle;
    }

    public void setContinueTitle(String continueTitle) {
        this.continueTitle = continueTitle;
    }
}
