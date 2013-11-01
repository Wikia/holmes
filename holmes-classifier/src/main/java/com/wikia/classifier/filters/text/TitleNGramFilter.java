package com.wikia.classifier.filters.text;

import com.wikia.classifier.input.structured.WikiPageStructure;
import com.wikia.classifier.text.Tokenizer;
import com.wikia.classifier.text.TokenizerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TitleNGramFilter extends NGramFilterBase {
    private static final long serialVersionUID = -1547489006707591850L;
    private static Logger logger = LoggerFactory.getLogger(TitleNGramFilter.class);

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
