package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 12.04.13
 * Time: 10:25
 */

import com.wikia.reader.filters.FilterBase;
import com.wikia.reader.text.matrix.Matrix;
import com.wikia.reader.text.matrix.Vector;
import com.wikia.reader.util.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SparseInstance;

import java.util.*;

public class AnnotatedMatrixToWekaInstancesFilter extends FilterBase<Matrix, Instances> {
    private static final long serialVersionUID = 5906815478415450072L;
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(AnnotatedMatrixToWekaInstancesFilter.class);
    private String classAttributeName;
    private List<String> classes;
    private List<String> attributeNames;

    public AnnotatedMatrixToWekaInstancesFilter(String classAttributeName, List<String> classes, List<String> attributeNames) {
        super(Matrix.class, Instances.class);
        this.classAttributeName = classAttributeName;
        this.classes = classes;
        this.attributeNames = attributeNames;
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
            Vector row=  matrix.getRow(rowName);
            SparseInstance instance = getSparseInstance(matrix, attributeIndices, rowName);
            instance.setDataset(instances);
            Iterator<String> iterator = row.getAnnotations().iterator();
            if( iterator.hasNext() ) {
                instance.setClassValue(iterator.next());
            }
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
            if(attributeIndices.containsKey(stringDoubleEntry.getKey())) {
                values[i] = stringDoubleEntry.getValue();
                indices[i] = attributeIndices.get(stringDoubleEntry.getKey());
                i++;
            }
        }
        ArrayUtils.sortBoth(indices, values);
        return new SparseInstance(1, values, indices, i);
    }

    private Map<String, Integer> getAttributeMap(Iterable<Attribute> attributes) {
        Map<String, Integer> map = new HashMap<>();
        int i=0;
        for(Attribute attribute: attributes) {
            map.put(attribute.name(), i++);
        }
        return map;
    }

    private Attribute getClassAttribute(Iterable<Attribute> attributes) {
        for(Attribute attribute: attributes) {
            if(attribute.name().equals(classAttributeName)) return attribute;
        }
        throw new IllegalStateException("No class attribute (name=" + classAttributeName +").");
    }

    private ArrayList<Attribute> getAttributes(Matrix matrix) {
        ArrayList<Attribute> attributes = new ArrayList<>();
        List<String> columnNames;
        if(attributeNames == null) {
            columnNames = new ArrayList<>(matrix.getColumnNames());
            Collections.sort(columnNames);
        } else {
            columnNames = attributeNames;
        }

        for(String name: columnNames) {
            attributes.add(new Attribute(name));
        }
        attributes.add(new Attribute(classAttributeName, classes));
        return attributes;
    }
}
