package com.wikia.classifier.filters.text;

import com.wikia.classifier.filters.FilterBase;
import com.wikia.classifier.util.matrix.Matrix;
import com.wikia.classifier.util.matrix.SparseMatrix;
import com.wikia.classifier.util.matrix.Vector;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        long cutoff = Math.round(threshold*rows.size());
        for(String colName: cols) {
            Vector vector = matrix.getColumnVector(colName);
            double sum = vector.getNonZeroValues().size();
            if( sum>cutoff ) {
                keepCols.add(colName);
            }
        }
        for(String row: rows) {
            Vector vector = matrix.getRowVector(row);
            for(Map.Entry<String, Double> entry: vector.getNonZeroValues().entrySet()) {
                if(keepCols.contains(entry.getKey())) {
                    sparseMatrix.put(row, entry.getKey(), entry.getValue());
                }
            }
        }
        return sparseMatrix;
    }
}
