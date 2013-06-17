package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 19:53
 */

import com.wikia.reader.filters.FilterBase;
import com.wikia.reader.text.matrix.Matrix;
import com.wikia.reader.util.ArrayUtils;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SparseInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MatrixToWekaInstancesFilter extends FilterBase<Matrix, Instances> {
    private static final long serialVersionUID = -1266925240964370102L;
    private String classAttributeName;

    protected MatrixToWekaInstancesFilter(String classAttributeName) {
        super(Matrix.class, Instances.class);
        this.classAttributeName = classAttributeName;
    }

    @Override
    protected Instances doFilter(Matrix matrix) {
        ArrayList<Attribute> attributes = getAttributes(matrix);
        Map<String, Integer> attributeIndices = getAttributeMap(attributes);
        Instances instances = new Instances("Matrix", attributes, matrix.getRowNames().size());
        if(classAttributeName != null) {
            Attribute classAttribute = getClassAttribute(attributes);
            instances.setClass(classAttribute);
        }

        for(String rowName: matrix.getRowNames()) {
            SparseInstance instance = getSparseInstance(matrix, attributeIndices, rowName);
            instance.setDataset(instances);
            instances.add(instance);
        }
        return instances;
    }

    private SparseInstance getSparseInstance(Matrix matrix, Map<String, Integer> attributeIndices, String rowName) {
        Map<String, Double> row = matrix.getRow(rowName).getNonZeroValues();
        double values[] = new double[row.size()];
        int indices[] = new int[row.size()];
        int i=0;
        for(Map.Entry<String, Double> stringDoubleEntry: row.entrySet()) {
            values[i] = stringDoubleEntry.getValue();
            indices[i] = attributeIndices.get(stringDoubleEntry.getKey());
            i++;
        }
        ArrayUtils.sortBoth(indices, values);
        return new SparseInstance(1, values, indices, row.size());
    }

    private Map<String, Integer> getAttributeMap(ArrayList<Attribute> attributes) {
        Map<String, Integer> map = new HashMap<>();
        int i=0;
        for(Attribute attribute: attributes) {
            map.put(attribute.name(), i++);
        }
        return map;
    }

    private Attribute getClassAttribute(ArrayList<Attribute> attributes) {
        for(Attribute attribute: attributes) {
            if(attribute.name() == classAttributeName) return attribute;
        }
        throw new IllegalStateException("No class attribute (name=" + classAttributeName +").");
    }

    private ArrayList<Attribute> getAttributes(Matrix matrix) {
        ArrayList<Attribute> attributes = new ArrayList<>();
        Set<String> columnNames = matrix.getColumnNames();
        for(String name: matrix.getColumnNames()) {
            attributes.add(new Attribute(name));
        }
        return attributes;
    }
}
