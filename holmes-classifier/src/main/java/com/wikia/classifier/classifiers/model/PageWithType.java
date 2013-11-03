package com.wikia.classifier.classifiers.model;

import com.wikia.api.model.Page;

public class PageWithType extends Page {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
