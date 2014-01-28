package com.wikia.api.model;

import java.util.List;



public class Page implements PageInfo {
    private Long wikiId;
    private Long pageId;
    private Long namespace;
    private String title;
    private String wikiText;

    public Page() { }

    public Page(Page page) {
        setWikiId(page.getWikiId());
        setPageId(page.getPageId());
        setNamespace(page.getNamespace());
        setTitle(page.getTitle());
        setWikiText(page.getWikiText());
    }

    public Long getWikiId() {
        return wikiId;
    }

    public void setWikiId(Long wikiId) {
        this.wikiId = wikiId;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getNamespace() {
        return namespace;
    }

    public void setNamespace(Long namespace) {
        this.namespace = namespace;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWikiText() {
        return wikiText;
    }

    public void setWikiText(String wikiText) {
        this.wikiText = wikiText;
    }

    @Override
    public List<PageInfo> getLinks() {
        throw new UnsupportedOperationException();
    }
}
