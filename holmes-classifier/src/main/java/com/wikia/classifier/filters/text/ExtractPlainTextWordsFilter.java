package com.wikia.classifier.filters.text;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.input.structured.WikiPageStructure;
import com.wikia.classifier.text.Tokenizer;
import com.wikia.classifier.text.TokenizerImpl;
import com.wikia.classifier.text.TokenizerStopwordsFilter;
import com.wikia.classifier.matrix.SparseMatrix;
import com.wikia.classifier.util.StopWordsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class ExtractPlainTextWordsFilter extends CollectionFilterBase<WikiPageStructure, SparseMatrix> {
    private static final long serialVersionUID = 3540949511501759679L;
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ExtractPlainTextWordsFilter.class);
    private String delimiters = " \r\n\t.,;:'\"()?!<>[]{}|";
    private List<String> stopWords = StopWordsHelper.defaultStopWords();
    private int minLength = 3;
    private int minOccurrence = 1;

    public ExtractPlainTextWordsFilter() {
        super(WikiPageStructure.class, SparseMatrix.class);
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageStructure> params) {
        SparseMatrix matrix = new SparseMatrix();
        Tokenizer tokenizer = new TokenizerImpl(delimiters);
        tokenizer = new TokenizerStopwordsFilter(tokenizer, stopWords, 3);
        for(WikiPageStructure wikiPageStructure: params) {
            Multiset<String> multiset = HashMultiset.create();
            for(String token: tokenizer.tokenize(wikiPageStructure.getPlain())) {
                multiset.add(token);
            }
            for(Multiset.Entry<String> stringEntry: multiset.entrySet()) {
                matrix.put(wikiPageStructure.getTitle(), stringEntry.getElement(), stringEntry.getCount());
            }
        }
        return matrix;
    }

    public String getDelimiters() {
        return delimiters;
    }

    public void setDelimiters(String delimiters) {
        this.delimiters = delimiters;
    }

    public List<String> getStopWords() {
        return stopWords;
    }

    public void setStopWords(List<String> stopWords) {
        this.stopWords = stopWords;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMinOccurrence() {
        return minOccurrence;
    }

    public void setMinOccurrence(int minOccurrence) {
        this.minOccurrence = minOccurrence;
    }

}
