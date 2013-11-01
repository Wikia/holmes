package com.wikia.classifier.matrix;

import java.util.Set;


public interface Matrix {
    double get(String row, String col);

    void put(String row, String col, double val);

    Vector getRowVector(String name);

    Vector getColumnVector(String name);

    Set<String> getRowNames();

    Set<String> getColumnNames();

    void merge(Matrix matrix);
}
