package com.wikia.api.model;

import java.util.List;

/**
 * Author: Artur Dwornik
 * Date: 17.06.13
 * Time: 23:16
 */
public interface PageInfo {
    Long getId();
    Long getNamespace();
    String getTitle();
    String getWikiText();
    List<PageInfo> getLinks();
}
