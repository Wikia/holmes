package com.wikia.classifier.classifiers;

import com.wikia.classifier.classifiers.model.PageWithType;

import java.util.List;

public interface ClassifierFactory {
    Classifier build(List<PageWithType> trainingSet);
}
