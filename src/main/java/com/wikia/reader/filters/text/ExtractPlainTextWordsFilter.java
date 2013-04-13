package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 15:39
 */

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.wikia.reader.filters.CollectionFilterBase;
import com.wikia.reader.input.structured.WikiPageStructure;
import com.wikia.reader.text.Tokenizer;
import com.wikia.reader.text.TokenizerImpl;
import com.wikia.reader.text.TokenizerStopwordsFilter;
import com.wikia.reader.text.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Stopwords;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

public class ExtractPlainTextWordsFilter extends CollectionFilterBase<WikiPageStructure, SparseMatrix> {
    private static Logger logger = LoggerFactory.getLogger(ExtractPlainTextWordsFilter.class);
    private final List<String> DefaultStopWords = defaultStopWords();
    private String delimiters = " \r\n\t.,;:'\"()?!<>[]{}|";
    private List<String> stopWords = DefaultStopWords;
    private int minLength = 3;
    private int minOccurrence = 1;

    protected ExtractPlainTextWordsFilter() {
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

    private static List<String> defaultStopWords() {
        Stopwords stopwords = new Stopwords();
        List<String> words = new ArrayList<>(100);
        Enumeration elements = stopwords.elements();
        while ( elements.hasMoreElements() ) {
            words.add( (String) elements.nextElement() );
        }
        return words;
    }
}
