package com.wikia.reader.text;

import java.util.List;

/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 17:38
 */
public interface Tokenizer {
    List<String> tokenize(String input);
}
