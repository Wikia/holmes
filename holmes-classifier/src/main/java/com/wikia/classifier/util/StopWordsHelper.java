package com.wikia.classifier.util;

import weka.core.Stopwords;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class StopWordsHelper {

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
