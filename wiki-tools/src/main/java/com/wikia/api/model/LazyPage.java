package com.wikia.api.model;
/**
 * Author: Artur Dwornik
 * Date: 17.06.13
 * Time: 23:29
 */

import com.wikia.api.util.LazyFuture;

import java.util.ArrayList;
import java.util.List;

public class LazyPage implements PageInfo {
    private LazyFuture<Long> futureId = LazyFuture.getNullLazyFuture();
    private LazyFuture<Long> futureNamespace = LazyFuture.getNullLazyFuture();
    private LazyFuture<String> futureTitle = LazyFuture.getNullLazyFuture();
    private LazyFuture<String> futureWikiText = LazyFuture.getNullLazyFuture();
    private LazyFuture<List<PageInfo>> futureLinks = LazyFuture.<List<PageInfo>>createNonLazy(new ArrayList<PageInfo>());

    @Override
    public String getWikiText() {
        return futureWikiText.falselySafeGet();
    }

    public void setWikiText(String wikiText) {
        futureWikiText = LazyFuture.createNonLazy(wikiText);
    }

    public LazyFuture<String> getFutureWikiText() {
        return futureWikiText;
    }

    public void setFutureWikiText(LazyFuture<String> futureWikiText) {
        this.futureWikiText = futureWikiText;
    }

    @Override
    public Long getPageId() {
        return getFutureId().falselySafeGet();
    }

    public LazyFuture<Long> getFutureId() {
        return futureId;
    }

    public void setFutureId(LazyFuture<Long> futureId) {
        this.futureId = futureId;
    }

    public void setId(Long id) {
        this.futureId = LazyFuture.createNonLazy(id);
    }

    public LazyFuture<Long> getFutureNamespace() {
        return futureNamespace;
    }

    @Override
    public Long getNamespace() {
        return getFutureNamespace().falselySafeGet();
    }

    public void setFutureNamespace(LazyFuture<Long> namespace) {
        this.futureNamespace = namespace;
    }

    public void setNamespace(Long id) {
        this.setFutureNamespace(LazyFuture.createNonLazy(id));
    }

    public LazyFuture<String> getFutureTitle() {
        return futureTitle;
    }

    @Override
    public String getTitle() {
        return getFutureTitle().falselySafeGet();
    }

    public void setFutureTitle(LazyFuture<String> futureTitle) {
        this.futureTitle = futureTitle;
    }

    public void setTitle(String title) {
        setFutureTitle(LazyFuture.createNonLazy(title));
    }

    public LazyFuture<List<PageInfo>> getFutureLinks() {
        return futureLinks;
    }

    @Override
    public List<PageInfo> getLinks() {
        return futureLinks.falselySafeGet();
    }

    public void setFutureLinks(LazyFuture<List<PageInfo>> futureLinks) {
        this.futureLinks = futureLinks;
    }

    public void setLinks(List<PageInfo> links) {
        setFutureLinks(LazyFuture.createNonLazy(links));
    }
}
