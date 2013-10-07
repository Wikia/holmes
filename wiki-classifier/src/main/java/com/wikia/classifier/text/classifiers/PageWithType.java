package com.wikia.classifier.text.classifiers;

import com.wikia.api.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageWithType extends Page {
    private static Logger logger = LoggerFactory.getLogger(PageWithType.class);
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
