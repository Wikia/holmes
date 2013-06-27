package com.wikia.classifier.input.structured;/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 16:26
 */

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiPageInternalLink {
    private static Logger logger = LoggerFactory.getLogger(WikiPageInternalLink.class);
    private String title;
    private String to;

    public WikiPageInternalLink(String title, String to) {
        this.title = title;
        this.to = to;
        if(StringUtils.isEmpty(title)) {
            this.title = to;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
