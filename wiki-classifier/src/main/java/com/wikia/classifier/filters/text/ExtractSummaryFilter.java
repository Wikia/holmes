package com.wikia.classifier.filters.text;
/**
 * Author: Artur Dwornik
 * Date: 22.07.13
 * Time: 19:24
 */

import com.wikia.classifier.input.structured.WikiPageStructure;

public class ExtractSummaryFilter extends NGramFilterBase {
    private static final long serialVersionUID = -7979464314134557696L;

    public ExtractSummaryFilter(int n) {
        super(n, "summary" + n + ":");
    }

    @Override
    protected String getTextSource(WikiPageStructure wikiPageStructure) {
        return wikiPageStructure.getSummary();
    }
}
