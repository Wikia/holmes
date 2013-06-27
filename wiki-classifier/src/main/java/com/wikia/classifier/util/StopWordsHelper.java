package com.wikia.classifier.util;/**
 * Author: Artur Dwornik
 * Date: 17.04.13
 * Time: 00:17
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Stopwords;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class StopWordsHelper {
    private static Logger logger = LoggerFactory.getLogger(StopWordsHelper.class);

    public static List<String> defaultStopWords() {
        Stopwords stopwords = new Stopwords();
        List<String> words = new ArrayList<>(100);
        Enumeration elements = stopwords.elements();
        while ( elements.hasMoreElements() ) {
            words.add( (String) elements.nextElement() );
        }
        return words;
    }
}
