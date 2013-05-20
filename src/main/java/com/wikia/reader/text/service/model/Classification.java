package com.wikia.reader.text.service.model;/**
 * Author: Artur Dwornik
 * Date: 13.04.13
 * Time: 11:07
 */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "classification")
public class Classification {
    private List<String> classes = new ArrayList<>();
    private List<Double> probabilities;
    private double weight;

    public Classification() {
    }

    public Classification(List<String> classes, List<Double> probabilities) {
        this(classes, probabilities, 1);
    }

    public Classification(List<String> classes, List<Double> probabilities, double weight) {
        this.classes = classes;
        this.probabilities = probabilities;
        this.weight = weight;
    }

    public Classification(List<String> classes, double[] probabilities) {
        this.classes = classes;
        this.probabilities = new ArrayList<Double>(probabilities.length);
        for(double d:probabilities) {
            this.probabilities.add(d);
        }
        weight = 1;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<Double> getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(List<Double> probabilities) {
        this.probabilities = probabilities;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
