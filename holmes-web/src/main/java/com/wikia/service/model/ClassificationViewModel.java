package com.wikia.service.model;

import com.wikia.classifier.classifiers.model.ClassRelevance;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Classification response model.
 *
 * Serialized JSON example:
 * {
 *   "classes":{
 *     "mini_game":0.0027487441002302026,
 *     "tv_episode":0.022617430793758688,
 *     "person":0.09817658022949176,
 *     "other":0.5444942564203317,
 *     "video_game":0.022427865727909266
 *   },
 *   "class":"other"
 * }
 */
@XmlRootElement(name = "classification")
public class ClassificationViewModel implements Serializable {
    private static final long serialVersionUID = -386473795640625588L;
    private static final String DEFAULT_FALLBACK_CLASS = "other";
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

    public static ClassificationViewModel getDefaultFallback() {
        ClassificationViewModel defaultResult = new ClassificationViewModel();
        Map<String, Double> classes = new HashMap<>();
        classes.put(DEFAULT_FALLBACK_CLASS, 1.0);
        defaultResult.setClasses(classes);
        defaultResult.setSingleClass(DEFAULT_FALLBACK_CLASS);
        return defaultResult;
    }
}
