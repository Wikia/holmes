package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 21:56
 */

import com.wikia.reader.filters.FilterBase;
import com.wikia.reader.text.matrix.Matrix;
import com.wikia.reader.text.matrix.SparseMatrix;
import com.wikia.reader.text.matrix.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class RemoveSparseTermsFilter extends FilterBase<Matrix, Matrix> {
    private static final long serialVersionUID = -9181355870778350163L;
    private double threshold;

    public RemoveSparseTermsFilter(double threshold) {
        super(Matrix.class, Matrix.class);
        this.threshold = threshold;
    }

    @Override
    protected Matrix doFilter(Matrix matrix) {
        SparseMatrix sparseMatrix = new SparseMatrix();
        Set<String> keepCols = new HashSet<>();
        Set<String> cols = matrix.getColumnNames();
        Set<String> rows = matrix.getRowNames();
        double cutoff = threshold*rows.size();
        for(String colName: cols) {
            Vector vector = matrix.getColVector(colName);
            double sum = vector.getNonZeroValues().size();
            if( sum>cutoff ) {
                keepCols.add(colName);
            }
        }
        for(String row: rows) {
            Vector vector = matrix.getRow(row);
            for(Map.Entry<String, Double> entry: vector.getNonZeroValues().entrySet()) {
                if(keepCols.contains(entry.getKey())) {
                    sparseMatrix.put(row, entry.getKey(), entry.getValue());
                }
            }
        }
        return sparseMatrix;
    }
}
