package com.wikia.reader.nlp.annie;

import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 22:09
 */
public class GateDocument {
    private static Logger logger = Logger.getLogger(GateDocument.class.toString());
    private String title;
    private String content;
    private String url;

    public GateDocument(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
