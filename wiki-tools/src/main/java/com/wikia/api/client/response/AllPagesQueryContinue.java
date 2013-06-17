package com.wikia.api.client.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author: Artur Dwornik
 * Date: 08.06.13
 * Time: 23:26
 */

public class AllPagesQueryContinue  implements Serializable {
    private static final long serialVersionUID = -2806964316608787972L;
    @SerializedName("allpages")
    private AllPagesAPContinue allPages;

    public AllPagesAPContinue getAllPages() {
        return allPages;
    }

    public void setAllPages(AllPagesAPContinue allPages) {
        this.allPages = allPages;
    }
}
