package com.wikia.reader.text.matrix;

import java.util.Set;

/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 13:48
 */
public interface Matrix {

    double get(String row, String col);

    void put(String row, String col, double val);

    SparseMatrix.VectorBase getRow(String name);

    SparseMatrix.VectorBase getColVector(String name);

    Set<String> getRowNames();

    Set<String> getColumnNames();

    void merge(Matrix matrix);
}
