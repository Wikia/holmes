package com.wikia.classifier.classifiers.training;

import com.wikia.classifier.classifiers.DefaultClassifierFactory;

public class ClassifierTrainerFactory {

    public ClassifierTrainer create() {
        return new DishonestClassifierTrainer( new DefaultClassifierFactory(), new DefaultClassifierVerifier());
    }
}
