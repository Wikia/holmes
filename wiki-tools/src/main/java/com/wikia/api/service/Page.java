package com.wikia.api.service;/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 22:23
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Page {
    private static Logger logger = LoggerFactory.getLogger(Page.class);
    private Long id;
    private Long namespace;
    private String title;
    private String wikiText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
