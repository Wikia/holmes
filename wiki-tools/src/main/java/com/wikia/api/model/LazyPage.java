package com.wikia.api.model;
/**
 * Author: Artur Dwornik
 * Date: 17.06.13
 * Time: 23:29
 */

import com.wikia.api.util.LazyFuture;

public class LazyPage extends PageBase {
    private LazyFuture<String> futureWikiText;

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
}
