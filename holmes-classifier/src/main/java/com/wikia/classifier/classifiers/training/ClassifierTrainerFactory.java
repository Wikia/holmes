package com.wikia.classifier.classifiers.training;

import com.wikia.classifier.classifiers.DefaultClassifierFactory;

import java.util.concurrent.Executors;

public class ClassifierTrainerFactory {
    private int noFolds = 5;
    private int noThreads = 6; // one thread per fold + one thread for the final classifier.

    public ClassifierTrainer create() {
        return new CrossValidatingClassifierTrainer(
                new DefaultClassifierFactory(),
                new DefaultClassifierVerifier(),
                new FoldingStrategyImpl(getNoFolds()),
                Executors.newFixedThreadPool(getNoThreads())
        );
    }

    public int getNoFolds() {
        return noFolds;
    }

    public void setNoFolds(int noFolds) {
        this.noFolds = noFolds;
    }

    public int getNoThreads() {
        return noThreads;
    }

    public void setNoThreads(int noThreads) {
        this.noThreads = noThreads;
    }
}
