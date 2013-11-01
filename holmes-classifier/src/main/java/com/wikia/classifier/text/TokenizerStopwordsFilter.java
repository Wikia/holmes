package com.wikia.classifier.text;

import com.wikia.classifier.util.StopWordsHelper;

import java.io.Serializable;
import java.util.*;

public class TokenizerStopwordsFilter implements Tokenizer, Serializable {
    private static final long serialVersionUID = -1217328004971373166L;
    private final Tokenizer tokenizer;
    private final Set<String> stopwords;
    private int minLength = 2;

    public TokenizerStopwordsFilter(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        stopwords = new HashSet<>(StopWordsHelper.defaultStopWords());
    }

    public TokenizerStopwordsFilter(Tokenizer tokenizer, Collection<String> stopWords, int minLength) {
        this.tokenizer = tokenizer;
        this.minLength = minLength;
        stopwords = new HashSet<>(stopWords);
    }

    @Override
    public List<String> tokenize(String input) {
        List<String> filteredTokens = new ArrayList<>();
        for(String token: tokenizer.tokenize(input)) {
            if( token.length() >= minLength && !stopwords.contains(token.toLowerCase()) ) {
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
