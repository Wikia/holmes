package com.wikia.classifier.classifiers.training;

import com.wikia.classifier.classifiers.DefaultClassifierFactory;

import java.util.concurrent.Executors;

public class ClassifierTrainerFactory {

    public ClassifierTrainer create() {
        return new CrossValidatingClassifierTrainer(
                new DefaultClassifierFactory(),
                new DefaultClassifierVerifier(),
                new FoldingStrategyImpl(5),
                Executors.newFixedThreadPool(3)
        );
    }
}
