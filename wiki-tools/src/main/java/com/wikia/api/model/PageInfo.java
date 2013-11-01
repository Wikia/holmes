package com.wikia.api.model;

import java.util.List;


public interface PageInfo {
    Long getPageId();
    Long getNamespace();
    String getTitle();
    String getWikiText();
    List<PageInfo> getLinks();
}
