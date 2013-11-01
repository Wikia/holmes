package com.wikia.classifier.text.classifiers;

import com.wikia.api.model.PageInfo;
import com.wikia.classifier.text.classifiers.model.ClassificationResult;
import com.wikia.classifier.text.classifiers.exceptions.ClassifyException;


public interface Classifier {
    public ClassificationResult classify(PageInfo source) throws ClassifyException;
}
