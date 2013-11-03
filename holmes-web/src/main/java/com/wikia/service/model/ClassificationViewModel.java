package com.wikia.service.model;

import com.wikia.classifier.classifiers.model.ClassRelevance;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name = "classification")
public class ClassificationViewModel implements Serializable {
    private static final long serialVersionUID = -386473795640625588L;
    @XmlAttribute(name = "class")
    @JsonProperty("class")
    private String singleClass;
    @XmlAttribute(name = "classes")
    private Map<String,Double> classes = new HashMap<>();

    @SuppressWarnings("unused")
    public String getSingleClass() {
        return singleClass;
    }

    public void setSingleClass(String singleClass) {
        this.singleClass = singleClass;
    }

    public Map<String, Double> getClasses() {
        return classes;
    }

    @SuppressWarnings("unused")
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
