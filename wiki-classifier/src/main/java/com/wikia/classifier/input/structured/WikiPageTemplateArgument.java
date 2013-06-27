package com.wikia.classifier.input.structured;/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 17:00
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiPageTemplateArgument {
    private static Logger logger = LoggerFactory.getLogger(WikiPageTemplateArgument.class);
    private String name;
    private String stringValue;

    public WikiPageTemplateArgument(String name, String value) {
        this.name = name;
        this.stringValue = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
