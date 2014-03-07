package com.wikia.classifier.classifiers.training;

import com.wikia.classifier.classifiers.model.PageWithType;

import java.util.List;

public class Fold {
    private final List<PageWithType> trainingSet;
    private final List<PageWithType> verificationSet;

    public Fold(List<PageWithType> trainingSet, List<PageWithType> verificationSet) {
        this.trainingSet = trainingSet;
        this.verificationSet = verificationSet;
    }

    public List<PageWithType> getTrainingSet() {
        return trainingSet;
    }

    public List<PageWithType> getVerificationSet() {
        return verificationSet;
    }
}
