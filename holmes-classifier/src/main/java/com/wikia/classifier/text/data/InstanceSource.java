package com.wikia.classifier.text.data;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InstanceSource {
    private URL wikiRoot;
    private String title;
    private Set<String> features;
    private Long id;

    public InstanceSource(URL wikiRoot, String title, Collection<String> features) {
        this(wikiRoot, title, new HashSet<>(features));
    }

    public InstanceSource(URL wikiRoot, long id, Collection<String> featuresCollection) {
        this.wikiRoot = wikiRoot;
        this.id = id;
        this.features = new HashSet<>(featuresCollection);
    }

    public InstanceSource(URL wikiRoot, String title, Set<String> features) {
        if(features == null) features = new HashSet<>();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstanceSource)) return false;

        InstanceSource that = (InstanceSource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (!wikiRoot.equals(that.wikiRoot)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wikiRoot.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
