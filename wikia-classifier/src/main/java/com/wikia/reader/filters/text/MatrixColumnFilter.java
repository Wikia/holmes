package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 14.04.13
 * Time: 11:31
 */

import com.wikia.reader.filters.FilterBase;
import com.wikia.reader.text.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class MatrixColumnFilter extends FilterBase<SparseMatrix, SparseMatrix> {
    private static Logger logger = LoggerFactory.getLogger(MatrixColumnFilter.class);
    Set<String> columnSet;

    public MatrixColumnFilter(Set<String> columnSet) {
        super(SparseMatrix.class,SparseMatrix.class);
        this.columnSet = columnSet;
    }

    @Override
    protected SparseMatrix doFilter(SparseMatrix input) {
        SparseMatrix matrix = new SparseMatrix();
        for(String col:input.getColumnNames()) {
            if(columnSet.contains(col)) {
                for(Map.Entry<String,Double> entry: input.getColVector(col).getNonZeroValues().entrySet()) {
                    matrix.put(entry.getKey(), col, entry.getValue());
                }
            }
        }
        return matrix;
    }
}
