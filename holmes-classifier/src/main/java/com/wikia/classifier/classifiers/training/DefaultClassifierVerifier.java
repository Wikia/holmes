package com.wikia.classifier.classifiers.training;

import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.exceptions.ClassifyException;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import com.wikia.classifier.classifiers.model.PageWithType;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultClassifierVerifier implements ClassifierVerifier {
    private static Logger logger = LoggerFactory.getLogger(DefaultClassifierVerifier.class);

    @Override
    public ClassifierTrainingResult verify(Classifier classifier, List<PageWithType> verificationSet) {
        if ( classifier == null ) {
            throw new NullArgumentException("classifier");
        }

        if ( verificationSet == null ) {
            throw new NullArgumentException("verificationSet");
        }

        if ( verificationSet.size() == 0 ) {
            throw new IllegalArgumentException("Empty verificationSet.");
        }

        List<ClassifierTrainingResult.ClassificationResultPair> verificationResults = new ArrayList<>();
        List<ClassifyException> errorList = new ArrayList<>();

        for ( PageWithType example: verificationSet ) {
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
