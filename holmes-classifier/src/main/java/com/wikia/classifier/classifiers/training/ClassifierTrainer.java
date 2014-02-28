package com.wikia.classifier.classifiers.training;

import com.wikia.classifier.classifiers.model.PageWithType;

import java.util.List;

public interface ClassifierTrainer {
    ClassifierTrainingResult train(List<PageWithType> trainingSet);
}
