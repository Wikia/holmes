package com.wikia.classifier.filters.text;

import com.wikia.classifier.input.WikiPageStructure;
import com.wikia.classifier.text.TokenizerImpl;

public class ExtractSummaryFilter extends NGramFilterBase {
    private static final long serialVersionUID = -7979464314134557696L;

    public ExtractSummaryFilter(int n) {
        super(n, "summary" + n + ":", new TokenizerImpl(" \r\n\t.,;:'\"()?!<>[]{}|") );
    }

    @Override
    protected String getTextSource(WikiPageStructure wikiPageStructure) {
        return wikiPageStructure.getSummary();
    }
}
