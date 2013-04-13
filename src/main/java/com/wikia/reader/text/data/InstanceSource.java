package com.wikia.reader.text.data;/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 18:56
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InstanceSource {
    private static Logger logger = LoggerFactory.getLogger(InstanceSource.class);
    private URL wikiRoot;
    private String title;
    private Set<String> features;

    public InstanceSource(URL wikiRoot, String title, Collection<String> features) {
        this(wikiRoot, title, new HashSet<>(features));
    }

    public InstanceSource(URL wikiRoot, String title, Set<String> features) {
        this.wikiRoot = wikiRoot;
        this.title = title;
        this.features = features;
    }

    public URL getWikiRoot() {
        return wikiRoot;
    }

    public void setWikiRoot(URL wikiRoot) {
        this.wikiRoot = wikiRoot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public void setFeatures(Set<String> features) {
        this.features = features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstanceSource that = (InstanceSource) o;

        if (!features.equals(that.features)) return false;
        if (!title.equals(that.title)) return false;
        if (!wikiRoot.equals(that.wikiRoot)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wikiRoot.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + features.hashCode();
        return result;
    }
}
