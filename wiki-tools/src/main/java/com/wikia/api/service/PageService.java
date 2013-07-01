package com.wikia.api.service;

import com.wikia.api.model.Page;
import com.wikia.api.model.PageInfo;

import java.io.IOException;

/**
 * Author: Artur Dwornik
 * Date: 11.06.13
 * Time: 15:22
 */
public interface PageService {
    PageInfo getPage(long pageId) throws IOException;
    PageInfo getPage(String title) throws IOException;

    Iterable<PageInfo> getPages();
}
