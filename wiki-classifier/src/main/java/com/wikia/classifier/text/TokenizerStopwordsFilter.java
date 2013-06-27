package com.wikia.classifier.text;/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 18:45
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Stopwords;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokenizerStopwordsFilter implements Tokenizer {
    private static Logger logger = LoggerFactory.getLogger(TokenizerStopwordsFilter.class);
    private final Tokenizer tokenizer;
    private final Stopwords stopwords = new Stopwords();
    private int minLength = 2;

    public TokenizerStopwordsFilter(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public TokenizerStopwordsFilter(Tokenizer tokenizer, Collection<String> stopWords, int minLength) {
        this.tokenizer = tokenizer;
        this.minLength = minLength;
        stopwords.clear();
        for(String word: stopWords) {
            stopwords.add(word);
        }
    }

    @Override
    public List<String> tokenize(String input) {
        List<String> tokens = tokenizer.tokenize(input);
        List<String> filteredTokens = new ArrayList<>();
        for(String token: tokenizer.tokenize(input)) {
            if( token.length() >= minLength && !stopwords.is(token) ) {
                filteredTokens.add(token);
            }
        }
        return filteredTokens;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }
}
