package com.wikia.reader.matrix;
/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 13:52
 */

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.Serializable;
import java.util.*;

public class SparseMatrix implements Matrix, Serializable {
    private static final long serialVersionUID = 4458562335464545378L;
    private HashBasedTable<String, String, Double> table = HashBasedTable.create();
    private Multimap<String, String> rowAnnotations = HashMultimap.create();
    private Multimap<String, String> colAnnotations = HashMultimap.create();

    @Override
    public double get(String rowName, String columnName) {
        Double doubleObject = table.get(rowName, columnName);
        if ( doubleObject == null ) {
            if ( !table.containsRow(rowName) ) { throw new NoSuchElementException(String.format("Row '%s' does not exist.", rowName)); }
            if ( !table.containsColumn(columnName) ) { throw new NoSuchElementException(String.format("Column '%s does not exist.", columnName)); }
            return 0;
        } else {
            return doubleObject;
        }
    }

    @Override
    public void put(String row, String col, double val) {
        table.put(row, col, val);
    }

    @Override
    public Vector getRowVector(String name) {
        return new RowVector(this, name);
    }

    @Override
    public Vector getColumnVector(String name) {
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
            for(Map.Entry<String, Double> entry: matrix.getRowVector(rowName).getNonZeroValues().entrySet()){
                table.put(rowName, entry.getKey(), entry.getValue());
            }
        }
    }

    private abstract class VectorBase implements Vector {

        protected abstract Map<String, Double> getMap();

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

        @Override
        public int size() {
            return getMap().size();
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

        @Override
        protected Map<String, Double> getMap() {
            return table.row(rowName);
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

        @Override
        protected Map<String, Double> getMap() {
            return table.row(colName);
        }
    }
}
