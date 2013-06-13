package com.wikia.reader.text;/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 17:39
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TokenizerImpl implements Tokenizer {
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
