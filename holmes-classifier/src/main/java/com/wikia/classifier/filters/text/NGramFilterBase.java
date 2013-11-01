package com.wikia.classifier.filters.text;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.input.structured.WikiPageStructure;
import com.wikia.classifier.matrix.SparseMatrix;
import com.wikia.classifier.text.Tokenizer;
import com.wikia.classifier.text.TokenizerImpl;
import com.wikia.classifier.text.TokenizerStopwordsFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class NGramFilterBase extends CollectionFilterBase<WikiPageStructure, SparseMatrix> {
    private static final long serialVersionUID = 325680535186348073L;
    private final Tokenizer sentenceTokenizer;
    private final Tokenizer wordTokenizer;
    private final String gramSeparator = "+";
    private final String prefix;
    private final int n;

    public NGramFilterBase(int n, String prefix) {
        this(n, prefix, new TokenizerStopwordsFilter(new TokenizerImpl(" \r\n\t.,;:'\"()?!<>[]{}|")));
    }

    public NGramFilterBase(int n, String prefix, Tokenizer wordTokenizer) {
        super(WikiPageStructure.class, SparseMatrix.class);
        this.prefix = prefix;
        this.sentenceTokenizer = new TokenizerImpl("\r\n.");
        this.wordTokenizer = wordTokenizer;
        this.n = n;
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageStructure> params) {
        SparseMatrix matrix = new SparseMatrix();
        for(WikiPageStructure wikiPageStructure: params) {
            Multiset<String> multiset = HashMultiset.create();
            for(String sentence: sentenceTokenizer.tokenize(getTextSource(wikiPageStructure))) {
                Collection<String> nGrams = getNGramsFromSentence( sentence );
                multiset.addAll( nGrams );
            }
            for(Multiset.Entry<String> stringEntry: multiset.entrySet()) {
                matrix.put(wikiPageStructure.getTitle(), stringEntry.getElement(), stringEntry.getCount());
            }
        }
        return matrix;
    }

    protected abstract String getTextSource(WikiPageStructure wikiPageStructure);

    protected Collection<String> getNGramsFromSentence(String sentence) {
        List<String> tokens = wordTokenizer.tokenize( sentence );
        List<String> nGrams = new ArrayList<>();
        for( int i = n-1; i<tokens.size(); i++ ) {
            String nGram = prefix;
            for( int j = (i - n + 1); j <= i; j++ ) {
                nGram += tokens.get( j );
                if( j != i ) {
                    nGram += gramSeparator;
                }
            }
            nGrams.add( nGram );
        }
        return nGrams;
    }

    public Tokenizer getSentenceTokenizer() {
        return sentenceTokenizer;
    }

    public Tokenizer getWordTokenizer() {
        return wordTokenizer;
    }

    public String getGramSeparator() {
        return gramSeparator;
    }

    public int getN() {
        return n;
    }
}
