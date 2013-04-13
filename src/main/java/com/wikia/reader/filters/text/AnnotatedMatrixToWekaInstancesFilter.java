package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 12.04.13
 * Time: 10:25
 */

import com.beust.jcommander.internal.Lists;
import com.wikia.reader.filters.FilterBase;
import com.wikia.reader.text.matrix.Matrix;
import com.wikia.reader.text.matrix.Vector;
import com.wikia.reader.util.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SparseInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class AnnotatedMatrixToWekaInstancesFilter extends FilterBase<Matrix, Instances> {
    private static Logger logger = LoggerFactory.getLogger(AnnotatedMatrixToWekaInstancesFilter.class);
    private String classAttributeName;
    private List<String> classes;

    protected AnnotatedMatrixToWekaInstancesFilter(String classAttributeName, List<String> classes) {
        super(Matrix.class, Instances.class);
        this.classAttributeName = classAttributeName;
        this.classes = classes;
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
            instance.setClassValue(row.getAnnotations().iterator().next());
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
        for(String name: matrix.getColumnNames()) {
            attributes.add(new Attribute(name));
        }
        attributes.add(new Attribute(classAttributeName, classes));
        return attributes;
    }
}
