package com.wikia.reader.input;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 00:44
 */
public class WikiTextChunk implements TextChunk {
    private String wikiText;
    private String sgml;
    private String title;

    public WikiTextChunk(String wikiText, String sgml, String title) {
        this.wikiText = wikiText;
        this.sgml = sgml;
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSgml() {
        return sgml;
    }

    @Override
    public String getWikiText() {
        return wikiText;
    }
}
