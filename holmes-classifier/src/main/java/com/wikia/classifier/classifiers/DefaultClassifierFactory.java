package com.wikia.classifier.classifiers;

import com.wikia.classifier.classifiers.model.PageWithType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;

import java.util.ArrayList;
import java.util.List;

public class DefaultClassifierFactory implements ClassifierFactory {
    private static Logger logger = LoggerFactory.getLogger(DefaultClassifierFactory.class);

    @Override
    public Classifier build(List<PageWithType> trainingSet) {
        List<CompositeClassifier.ClassifierEntry> classifiers = new ArrayList<>();
        try {
            /*
            logger.info("train IBk");
            classifiers.add(new CompositeClassifier.ClassifierEntry("IBk",
                    new ClassifierBuilder(new IBk()).train(pageInfos), 0.3));
            */

            logger.info("train J48");
            classifiers.add(new CompositeClassifier.ClassifierEntry("J48",
                    new ClassifierBuilder(new J48()).train(trainingSet), 1));

            logger.info("train SMO");
            classifiers.add(new CompositeClassifier.ClassifierEntry("SMO",
                    new ClassifierBuilder(new SMO())
                            .setExtractSummary1Grams(true)
                            .setExtractSummary2Grams(true)
                            .setExtractSummary3Grams(true)
                            .train(trainingSet), 1));

            logger.info("train RandomForest");
            RandomForest randomForest = new RandomForest(); randomForest.setNumTrees(200);
            classifiers.add(new CompositeClassifier.ClassifierEntry("RandomForest",
                    new ClassifierBuilder(randomForest).train(trainingSet), 1));

            logger.info("train NaiveBayes");
            classifiers.add(new CompositeClassifier.ClassifierEntry("NaiveBayes",
                    new ClassifierBuilder(new NaiveBayes()).train(trainingSet), 1));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new CompositeClassifier(classifiers);
    }
}
