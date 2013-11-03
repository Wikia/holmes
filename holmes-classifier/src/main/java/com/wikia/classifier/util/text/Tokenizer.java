package com.wikia.classifier.util.text;

import java.util.List;


public interface Tokenizer {
    List<String> tokenize(String input);
}
