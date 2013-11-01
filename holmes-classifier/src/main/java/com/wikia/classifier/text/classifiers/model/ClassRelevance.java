package com.wikia.classifier.text.classifiers.model;


import java.io.Serializable;

public class ClassRelevance implements Serializable, Comparable<ClassRelevance> {
    private static final long serialVersionUID = -551090165394331754L;
    private String className;
    private double relevance;

    public ClassRelevance(String className, double relevance) {
        this.className = className;
        this.relevance = relevance;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getRelevance() {
        return relevance;
    }

    public void setRelevance(double relevance) {
        this.relevance = relevance;
    }

    @Override
    public int compareTo(ClassRelevance o) {
        return ((Double) getRelevance()).compareTo(o.getRelevance());
    }
}
