package com.wikia.reader.text;

import com.wikia.reader.input.structured.WikiPageStructure;

import java.util.Map;

/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 17:23
 */
public interface Vectorizer {
    public Map<String, Double> vectorize(WikiPageStructure structure);
}
