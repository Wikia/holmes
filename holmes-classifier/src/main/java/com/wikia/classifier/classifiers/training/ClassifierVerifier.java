package com.wikia.classifier.classifiers.training;

import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.model.PageWithType;

import java.util.List;

public interface ClassifierVerifier {
    ClassifierTrainingResult verify(Classifier classifier, List<PageWithType> verificationSet);
}
