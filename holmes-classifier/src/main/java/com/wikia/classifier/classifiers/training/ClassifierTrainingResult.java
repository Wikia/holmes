package com.wikia.classifier.classifiers.training;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.exceptions.ClassifyException;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import com.wikia.classifier.classifiers.model.PageWithType;

import java.util.List;

/**
 * Encapsulates results of Classifier training.
 */
public class ClassifierTrainingResult {
    /**
     * True if input represents successful classification.
     */
    private static final Predicate<ClassificationResultPair> successfullyClassifiedPredicate = new Predicate<ClassificationResultPair>() {
        @Override
        public boolean apply(ClassificationResultPair input) {
            return input.isSuccess();
        }
    };

    /**
     * True if input represents negative error: Expected some type but got "other".
     */
    private static final Predicate<ClassificationResultPair> falseNegativeErrorPredicate = new Predicate<ClassificationResultPair>() {
        @Override
        public boolean apply(ClassificationResultPair input) {
            return !input.isSuccess() && input.getResult().getSingleClass().equals("other");
        }
    };

    /**
     * True if input represents positive error: Expected "other" but got some type.
     */
    private static final Predicate<ClassificationResultPair> falsePositiveErrorPredicate = new Predicate<ClassificationResultPair>() {
        @Override
        public boolean apply(ClassificationResultPair input) {
            return !input.isSuccess() && input.getPage().getType().equals("other");
        }
    };

    /**
     * True if input represents wrong type error. Got different type than expected.
     */
    private static final Predicate<ClassificationResultPair> wrongTypeErrorPredicate = new Predicate<ClassificationResultPair>() {
        @Override
        public boolean apply(ClassificationResultPair input) {
            return !input.isSuccess()
                    && !input.getPage().getType().equals("other")
                    && !input.getResult().getSingleClass().equals("other");
        }
    };

    /**
     * Trained Classifier
     */
    private final Classifier classifier;
    /**
     * Results of verification phase.
     */
    private final List<ClassificationResultPair> classificationResultPairList;
    /**
     * Errors that occurred during classification.
     */
    private final List<ClassifyException> nonFatalErrors;

    public ClassifierTrainingResult(Classifier classifier,
                                    List<ClassificationResultPair> classificationResultPairList,
                                    List<ClassifyException> nonFatalErrors) {

        this.classifier = classifier;
        this.classificationResultPairList = classificationResultPairList;
        this.nonFatalErrors = nonFatalErrors;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public List<ClassificationResultPair> getClassificationResultPairList() {
        return classificationResultPairList;
    }

    public List<ClassifyException> getNonFatalErrors() {
        return nonFatalErrors;
    }

    /**
     * Computes Ratio of successful to unsuccessful classification results.
     * @return Floating point value in range [0,1].
     */
    public double getSuccessRate() {
        return (double) Iterables.size(Iterables.filter(classificationResultPairList, successfullyClassifiedPredicate)) /
                (double) Iterables.size(classificationResultPairList);
    }

    /**
     * Computes Ratio errors where assigned type was "other", but expected was different
     * @return Floating point value in range [0,1].
     */
    public double getFalseNegativeErrorRate() {
        return (double) Iterables.size(Iterables.filter(classificationResultPairList, falseNegativeErrorPredicate)) /
                (double) Iterables.size(classificationResultPairList);
    }

    /**
     * Computes Ratio errors where assigned type was "other", but expected was different
     * @return Floating point value in range [0,1].
     */
    public double getFalsePositiveErrorRate() {
        return (double) Iterables.size(Iterables.filter(classificationResultPairList, falsePositiveErrorPredicate)) /
                (double) Iterables.size(classificationResultPairList);
    }

    /**
     * Computes Ratio errors where assigned type was "other", but expected was different
     * @return Floating point value in range [0,1].
     */
    public double getWrongTypeErrorRate() {
        return (double) Iterables.size(Iterables.filter(classificationResultPairList, wrongTypeErrorPredicate)) /
                (double) Iterables.size(classificationResultPairList);
    }

    public static class ClassificationResultPair {
        private final PageWithType page;
        private final ClassificationResult result;

        public ClassificationResultPair(PageWithType page, ClassificationResult result) {
            this.page = page;
            this.result = result;
        }

        public PageWithType getPage() {
            return page;
        }

        public ClassificationResult getResult() {
            return result;
        }

        public boolean isSuccess() {
            return page.getType().equals(result.getSingleClass());
        }
    }
}
