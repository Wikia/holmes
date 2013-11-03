package com.wikia.classifier.wikitext;

public class WikiPageTemplateArgument {
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
