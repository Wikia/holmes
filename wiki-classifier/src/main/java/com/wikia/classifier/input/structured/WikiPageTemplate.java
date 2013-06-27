package com.wikia.classifier.input.structured;/**
 * Author: Artur Dwornik
 * Date: 16.04.13
 * Time: 22:56
 */

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WikiPageTemplate {
    private static Logger logger = LoggerFactory.getLogger(WikiPageTemplate.class);
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
