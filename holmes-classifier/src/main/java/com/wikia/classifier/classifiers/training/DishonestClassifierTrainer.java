package com.wikia.classifier.classifiers.training;

import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.ClassifierFactory;
import com.wikia.classifier.classifiers.exceptions.ClassifyException;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import com.wikia.classifier.classifiers.model.PageWithType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Trains and verifies model using same input set.
 */
public class DishonestClassifierTrainer implements ClassifierTrainer {
    private static Logger logger = LoggerFactory.getLogger(DishonestClassifierTrainer.class);
    private final ClassifierFactory classifierFactory;

    public DishonestClassifierTrainer(ClassifierFactory classifierFactory) {
        this.classifierFactory = classifierFactory;
    }

    @Override
    public ClassifierTrainingResult train(List<PageWithType> trainingSet) {
        Classifier classifier = classifierFactory.build(trainingSet);

        return verify(trainingSet, classifier);
    }

    private ClassifierTrainingResult verify(List<PageWithType> trainingSet, Classifier classifier) {
        List<ClassifierTrainingResult.ClassificationResultPair> verificationResults = new ArrayList<>();
        List<ClassifyException> errorList = new ArrayList<>();

        for ( PageWithType example: trainingSet ) {
            try {
                ClassificationResult classificationResult = classifier.classify(example);

                verificationResults.add(new ClassifierTrainingResult.ClassificationResultPair(example, classificationResult));
            } catch (ClassifyException e) {
                logger.error(String.format("Classifying of %s (%d) caused error.", example.getTitle(), example.getWikiId()), e );
                errorList.add(e);
            }
        }

        return new ClassifierTrainingResult(classifier,
                Collections.unmodifiableList(verificationResults),
                Collections.unmodifiableList(errorList));
    }
}
