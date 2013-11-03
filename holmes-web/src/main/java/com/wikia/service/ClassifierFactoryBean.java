package com.wikia.service;

import com.wikia.classifier.Resources;
import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.DefaultClassifierFactory;
import com.wikia.classifier.classifiers.serialization.GZippedClassifierFileFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ClassifierFactoryBean {
    private static Logger logger = LoggerFactory.getLogger(ClassifierFactoryBean.class);
    private DefaultClassifierFactory classifierFactory = new DefaultClassifierFactory();
    private String pathToClassifier = null;
    private boolean useExampleClassifier = false;

    public Classifier getClassifier() throws IOException {
        if ( isUseExampleClassifier() ) {
            logger.info("Use example classifier");
            return classifierFactory.build(Resources.getExampleSet());
        } else {
            logger.info("Use classifier in following path: " + getPathToClassifier());
            return new GZippedClassifierFileFormat().read(getPathToClassifier());
        }
    }

    public String getPathToClassifier() {
        return pathToClassifier;
    }

    public void setPathToClassifier(String pathToClassifier) {
        this.pathToClassifier = pathToClassifier;
    }

    public boolean isUseExampleClassifier() {
        return useExampleClassifier;
    }

    public void setUseExampleClassifier(boolean useExampleClassifier) {
        this.useExampleClassifier = useExampleClassifier;
    }
}
