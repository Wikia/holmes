package com.wikia.reader.input.structured;/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 16:25
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiPageCategory {
    private static Logger logger = LoggerFactory.getLogger(WikiPageCategory.class);
    private String title;

    public WikiPageCategory(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
