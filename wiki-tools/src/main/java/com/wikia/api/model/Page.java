package com.wikia.api.model;/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 22:23
 */

public class Page extends PageBase {
    private String wikiText;

    @Override
    public String getWikiText() {
        return wikiText;
    }

    public void setWikiText(String wikiText) {
        this.wikiText = wikiText;
    }
}
