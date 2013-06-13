package com.wikia.reader.text.matrix;
/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 13:52
 */

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SparseMatrix implements Matrix, Serializable {
    private Table<String, String, Double> table = HashBasedTable.create();
    private Multimap<String, String> rowAnnotations = HashMultimap.create();
    private Multimap<String, String> colAnnotations = HashMultimap.create();

    @Override
    public double get(String row, String col) {
        return table.get(row,col);
    }

    @Override
    public void put(String row, String col, double val) {
        table.put(row, col, val);
    }

    @Override
    public VectorBase getRow(String name) {
        return new RowVector(this, name);
    }

    @Override
    public VectorBase getColVector(String name) {
        return new ColumnVector(this, name);
    }

    @Override
    public Set<String> getRowNames() {
        return table.rowKeySet();
    }

    @Override
    public Set<String> getColumnNames() {
        return table.columnKeySet();
    }

    @Override
    public void merge(Matrix matrix) {
        for(String rowName: matrix.getRowNames()) {
            for(Map.Entry<String, Double> entry: matrix.getRow(rowName).getNonZeroValues().entrySet()){
                table.put(rowName, entry.getKey(), entry.getValue());
            }
        }
    }

    public abstract class VectorBase implements Vector {

        @Override
        public double sumNonZeroValues() {
            double result = 0;
            for(double val: getNonZeroValues().values()) {
                result += val;
            }
            return result;
        }

        @Override
        public double get(String name) {
            Double val = getNonZeroValues().get(name);
            return val == null ? 0 : val;
        }

        @Override
        public void put(String name, double value) {
            if(value == 0) {
                getNonZeroValues().remove(name);
            } else {
                getNonZeroValues().put(name, value);
            }
        }
    }

    public class RowVector extends VectorBase {
        private final SparseMatrix sparseMatrix;
        private final String rowName;

        public RowVector(SparseMatrix sparseMatrix, String rowName) {
            this.sparseMatrix = sparseMatrix;
            this.rowName = rowName;
        }

        /*
        @Override
        public int getIndex() {
            return rowIndex;
        }
        */
        @Override
        public String getName() {
            return rowName;
        }

        @Override
        public Collection<String> getAnnotations() {
            return sparseMatrix.rowAnnotations.get(rowName);
        }

        @Override
        public Map<String, Double> getNonZeroValues() {
            return sparseMatrix.table.row(rowName);
        }
    }
    public class ColumnVector extends VectorBase {
        private final SparseMatrix sparseMatrix;
        private final String colName;
        //private final int colIndex;

        public ColumnVector(SparseMatrix sparseMatrix, String colName) {
            this.sparseMatrix = sparseMatrix;
            this.colName = colName;
            // this.colIndex = colIndex;
        }

        /*
        @Override
        public int getIndex() {
            return colIndex;
        }
        */

        @Override
        public String getName() {
            return colName;
        }

        @Override
        public Collection<String> getAnnotations() {
            return sparseMatrix.colAnnotations.get(colName);
        }

        @Override
        public Map<String, Double> getNonZeroValues() {
            return sparseMatrix.table.column(colName);
        }
    }
}
