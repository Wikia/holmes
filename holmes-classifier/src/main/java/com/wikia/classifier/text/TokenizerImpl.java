package com.wikia.classifier.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TokenizerImpl implements Tokenizer, Serializable {
    private static final long serialVersionUID = 3162722594450496274L;
    private static Logger logger = LoggerFactory.getLogger(TokenizerImpl.class);
    private String delimiters;

    public TokenizerImpl() {
        this(" \r\n\t.,;:'\"()?!<>[]{}|");
    }

    public TokenizerImpl(String delimiters) {
        this.delimiters = delimiters;
    }

    @Override
    public List<String> tokenize(String input) {
        StringTokenizer stringTokenizer = new StringTokenizer(input, delimiters);
        int count = stringTokenizer.countTokens();
        List<String> ret = new ArrayList<>();
        while (stringTokenizer.hasMoreElements()) {
            ret.add(stringTokenizer.nextToken().toLowerCase());
        }
        return ret;
    }
}
