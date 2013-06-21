package com.wikia.reader.text.classifiers.model;
/**
 * Author: Artur Dwornik
 * Date: 21.06.13
 * Time: 00:11
 */

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "classificationResult")
public class ClassificationResult implements Serializable {
    private static final long serialVersionUID = 7005101395687902128L;
    private String singleClass;
    private List<ClassRelevance> classes;

    public ClassificationResult(String singleClass, List<ClassRelevance> classes) {
        this.singleClass = singleClass;
        this.classes = classes;
    }

    public String getSingleClass() {
        return singleClass;
    }

    public void setSingleClass(String singleClass) {
        this.singleClass = singleClass;
    }

    public List<ClassRelevance> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassRelevance> classes) {
        this.classes = classes;
    }
}
