package com.wikia.classifier.classifiers.training;

import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.ClassifierFactory;
import com.wikia.classifier.classifiers.model.PageWithType;

import java.util.List;

/**
 * Trains and verifies model using same input set.
 * Results of such verification are not reliable.
 */
public class DishonestClassifierTrainer implements ClassifierTrainer {
    private final ClassifierFactory classifierFactory;
    private final ClassifierVerifier verifier;

    public DishonestClassifierTrainer(ClassifierFactory classifierFactory, ClassifierVerifier verifier) {
        this.classifierFactory = classifierFactory;
        this.verifier = verifier;
    }

    @Override
    public ClassifierTrainingResult train(List<PageWithType> trainingSet) {
        Classifier classifier = classifierFactory.build(trainingSet);

        return verifier.verify(classifier, trainingSet);
    }
}

