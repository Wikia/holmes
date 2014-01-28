package com.wikia.classifier.domain;

import com.wikia.api.model.Page;
import com.wikia.classifier.classifiers.model.ClassificationResult;

public class ClassifiedPage extends Page {
    private ClassificationResult classification;

    public ClassifiedPage(Page page, ClassificationResult classification) {
        super(page);
        setClassification(classification);
    }

    @SuppressWarnings("unused")
    public ClassificationResult getClassification() {
        return classification;
    }

    public void setClassification(ClassificationResult classification) {
        this.classification = classification;
    }
}
