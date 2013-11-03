package com.wikia.classifier.filters.text;

import com.wikia.classifier.wikitext.WikiPageStructure;
import com.wikia.classifier.util.text.Tokenizer;
import com.wikia.classifier.util.text.TokenizerImpl;

public class TitleNGramFilter extends NGramFilterBase {
    private static final long serialVersionUID = -1547489006707591850L;

    public TitleNGramFilter( int maxNGram ) {
        this( maxNGram, new TokenizerImpl(" \r\n\t.,;:'\"()?!<>[]{}|") );
    }

    public TitleNGramFilter( int maxNGram, Tokenizer tokenizer ) {
        super( maxNGram, "title:", tokenizer );
    }

    @Override
    protected String getTextSource( WikiPageStructure wikiPageStructure ) {
        return wikiPageStructure.getTitle();
    }
}
