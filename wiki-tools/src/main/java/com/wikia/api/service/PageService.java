package com.wikia.api.service;

import java.io.IOException;

/**
 * Author: Artur Dwornik
 * Date: 11.06.13
 * Time: 15:22
 */
public interface PageService {
    Page getPage(long pageId) throws IOException;

    Iterable<Page> getPages();
}
