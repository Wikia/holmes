package com.wikia.reader.weka;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 03.04.13
 * Time: 23:36
 */
public class InstancesVectorizer {
    private static Logger logger = Logger.getLogger(InstancesVectorizer.class.toString());

    public Instances vectorize(Instances dataSet) {
        try {
            StringToWordVector stringToWordVector = new StringToWordVector();

            stringToWordVector.setInputFormat(dataSet);
            Instances data = Filter.useFilter(dataSet, stringToWordVector);
            data = addId(data, dataSet);
            return data;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Unexpected exception.", e);
            throw new IllegalStateException("Not expected exception", e);
        }
    }

    private Instances addId(Instances data, Instances originalSet) throws Exception {
        ArrayList<Attribute> attributes = new ArrayList<>(data.numAttributes()+1);
        for(int i=0; i<data.numAttributes(); i++) {
            attributes.add(data.attribute(i));
        }
        Attribute title = new Attribute("__title__", (ArrayList<String>) null);
        attributes.add(title);

        Instances instances = new Instances("", attributes, data.numInstances());
        instances.setClass(title);
        for(int i=0; i<data.numInstances(); i++) {
            Instance clone = (Instance) data.get(i).copy();
            clone.setDataset(instances);
            clone.setValue(title, originalSet.get(i).stringValue(0));
            instances.add(clone);
        }
        return instances;
    }
}
