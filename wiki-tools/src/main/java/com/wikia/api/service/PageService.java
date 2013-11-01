package com.wikia.api.service;

import com.wikia.api.model.PageInfo;

import java.io.IOException;


public interface PageService {
    PageInfo getPage(long pageId) throws IOException;
    PageInfo getPage(String title) throws IOException;

    Iterable<PageInfo> getPages();
}
