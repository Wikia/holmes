package com.wikia.reader.input.structured;/**
 * Author: Artur Dwornik
 * Date: 13.04.13
 * Time: 19:15
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiPageSection {
    private static Logger logger = LoggerFactory.getLogger(WikiPageSection.class);
    private String title;

    public WikiPageSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
