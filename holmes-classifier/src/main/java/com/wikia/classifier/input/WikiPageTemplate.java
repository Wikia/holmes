package com.wikia.classifier.input;

import com.google.common.collect.Lists;

import java.util.List;

public class WikiPageTemplate {
    private final List<String> childNames;
    private String name;

    public WikiPageTemplate(String name, String[] childNames) {
        this.name = name;
        this.childNames = Lists.newArrayList(childNames);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getChildNames() {
        return childNames;
    }
}
