package com.wikia.classifier.text;

import java.util.List;


public interface Tokenizer {
    List<String> tokenize(String input);
}
