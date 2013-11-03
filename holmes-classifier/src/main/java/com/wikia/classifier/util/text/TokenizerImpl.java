package com.wikia.classifier.util.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TokenizerImpl implements Tokenizer, Serializable {
    private static final long serialVersionUID = 3162722594450496274L;
    private String delimiters;

    public TokenizerImpl(String delimiters) {
        this.delimiters = delimiters;
    }

    @Override
    public List<String> tokenize(String input) {
        StringTokenizer stringTokenizer = new StringTokenizer(input, delimiters);
        List<String> ret = new ArrayList<>();
        while (stringTokenizer.hasMoreElements()) {
            ret.add(stringTokenizer.nextToken().toLowerCase());
        }
        return ret;
    }
}
