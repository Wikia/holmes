package com.wikia.service.model;
/**
 * Author: Artur Dwornik
 * Date: 21.06.13
 * Time: 15:47
 */

import com.wikia.classifier.text.classifiers.model.ClassRelevance;
import com.wikia.classifier.text.classifiers.model.ClassificationResult;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name = "classification")
public class ClassificationViewModel implements Serializable {
    @XmlAttribute(name = "class")
    @JsonProperty("class")
    private String singleClass;
    @XmlAttribute(name = "classes")
    private Map<String,Double> classes = new HashMap<>();

    public String getSingleClass() {
        return singleClass;
    }

    public void setSingleClass(String singleClass) {
        this.singleClass = singleClass;
    }

    public Map<String, Double> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, Double> classes) {
        this.classes = classes;
    }

    public static ClassificationViewModel fromClassificationResult(ClassificationResult classificationResult) {
        ClassificationViewModel viewModel = new ClassificationViewModel();
        viewModel.setSingleClass(classificationResult.getSingleClass());
        for(ClassRelevance classRelevance: classificationResult.getClasses()) {
            viewModel.getClasses().put(classRelevance.getClassName(), classRelevance.getRelevance());
        }
        return viewModel;
    }
}
