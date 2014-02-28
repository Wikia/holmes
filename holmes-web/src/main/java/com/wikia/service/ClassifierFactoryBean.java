package com.wikia.service;

import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.serialization.GZippedClassifierFileFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Locates serialized classifier in filesystem or classpath
 */
public class ClassifierFactoryBean {
    private static Logger logger = LoggerFactory.getLogger(ClassifierFactoryBean.class);
    private String pathToClassifier = null;

    public Classifier getClassifier() throws IOException {
        logger.info("Use classifier in following path: " + getPathToClassifier());
        if (isClassPathResource()) {
            try (InputStream resourceStream = getClasspathResourceStream()) {
                return new GZippedClassifierFileFormat().read(resourceStream);
            }
        } else {
            return new GZippedClassifierFileFormat().read(getPathToClassifier());
        }
    }

    public String getPathToClassifier() {
        return pathToClassifier;
    }

    public void setPathToClassifier(String pathToClassifier) {
        this.pathToClassifier = pathToClassifier;
    }

    protected boolean isClassPathResource() {
        return getPathToClassifier() != null
                && getPathToClassifier().trim().startsWith("classpath:");
    }

    protected InputStream getClasspathResourceStream() {
        if (!isClassPathResource()) {
            throw new IllegalStateException("Trying to get classpath resource but path doesn't look like from classpath.");
        }
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(getPathToClassifier().trim().substring(10));
    }
}
