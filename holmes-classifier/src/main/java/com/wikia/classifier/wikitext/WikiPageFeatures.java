package com.wikia.classifier.wikitext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class WikiPageFeatures {
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(WikiPageFeatures.class.toString());
    private String title = "";
    private String plain = "";
    private String summary = "";
    private final List<WikiPageCategory> categories = new ArrayList<>();
    private final List<WikiPageInternalLink> internalLinks = new ArrayList<>();
    private final List<WikiPageTemplateArgument> templateArguments = new ArrayList<>();
    private final List<WikiPageSection> sections = new ArrayList<>();
    private final List<WikiPageTemplate> templates = new ArrayList<>();

    public WikiPageFeatures(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public List<WikiPageCategory> getCategories() {
        return categories;
    }

    public List<WikiPageInternalLink> getInternalLinks() {
        return internalLinks;
    }

    public List<WikiPageTemplateArgument> getTemplateArguments() {
        return templateArguments;
    }

    public List<WikiPageSection> getSections() {
        return sections;
    }

    public List<WikiPageTemplate> getTemplates() {
        return templates;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }
}
