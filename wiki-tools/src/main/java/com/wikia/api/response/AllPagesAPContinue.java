package com.wikia.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 23:32
 */

public class AllPagesAPContinue {
    @SerializedName("apcontinue")
    private String continueTitle;

    public String getContinueTitle() {
        return continueTitle;
    }

    public void setContinueTitle(String continueTitle) {
        this.continueTitle = continueTitle;
    }
}
