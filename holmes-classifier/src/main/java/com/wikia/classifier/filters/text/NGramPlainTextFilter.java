package com.wikia.classifier.filters.text;

import com.wikia.classifier.wikitext.WikiPageStructure;

/**
 * TODO: Use it
 */
@SuppressWarnings("unused")
public class NGramPlainTextFilter extends NGramFilterBase {
    private static final long serialVersionUID = 1446235602715296091L;

    public NGramPlainTextFilter(int n) {
        super(n, "plain" + n + ":");
    }

    @Override
    protected String getTextSource(WikiPageStructure wikiPageStructure) {
        return wikiPageStructure.getPlain();
    }
}
