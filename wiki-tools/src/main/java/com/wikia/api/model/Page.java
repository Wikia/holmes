package com.wikia.api.model;

import java.util.List;

/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 22:23
 */

public class Page extends PageBase {
    private String wikiText;
    private List<PageInfo> links;

    @Override
    public String getWikiText() {
        return wikiText;
    }

    public void setWikiText(String wikiText) {
        this.wikiText = wikiText;
    }

    public List<PageInfo> getLinks() {
        return links;
    }

    public void setLinks(List<PageInfo> links) {
        this.links = links;
    }
}
