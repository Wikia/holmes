package com.wikia.classifier.filters.text;/**
 * Author: Artur Dwornik
 * Date: 28.04.13
 * Time: 13:57
 */

import com.wikia.classifier.input.structured.WikiPageStructure;

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
