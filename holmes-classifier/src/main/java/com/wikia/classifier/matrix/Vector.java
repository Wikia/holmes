package com.wikia.classifier.matrix;


import java.util.Collection;
import java.util.Map;

public interface Vector {
    String getName();

    Collection<String> getAnnotations();

    Map<String, Double> getNonZeroValues();

    double sumNonZeroValues();

    double get(String name);

    void put(String name, double value);

    int size();
}
