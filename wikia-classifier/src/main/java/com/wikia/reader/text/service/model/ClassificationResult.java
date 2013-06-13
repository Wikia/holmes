package com.wikia.reader.text.service.model;/**
 * Author: Artur Dwornik
 * Date: 14.04.13
 * Time: 10:43
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "classificationResult")
public class ClassificationResult {
    private static Logger logger = LoggerFactory.getLogger(ClassificationResult.class);
    private String classifierName;
    private String classification;
    private double[] probabilities;


    public ClassificationResult() {
        this(null);
    }

    public ClassificationResult(String classifierName) {
        this(classifierName, null);
    }
    public ClassificationResult(String classifierName, String classification) {
        this(classifierName, classification, null);
    }

    public ClassificationResult(String classifierName, String classification, double[] probabilities) {
        this.classifierName = classifierName;
        this.classification = classification;
        this.probabilities = probabilities;
    }

    public String getClassifierName() {
        return classifierName;
    }

    public void setClassifierName(String classifierName) {
        this.classifierName = classifierName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
