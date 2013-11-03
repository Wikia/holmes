package com.wikia.classifier.classifiers;

import com.wikia.api.model.PageInfo;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import com.wikia.classifier.classifiers.exceptions.ClassifyException;

public interface Classifier {
    public ClassificationResult classify(PageInfo source) throws ClassifyException;
}
