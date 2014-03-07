package com.wikia.classifier.classifiers.training;

import com.wikia.classifier.classifiers.model.PageWithType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.NullArgumentException;

import java.util.List;

public class Fold {
    private final List<PageWithType> trainingSet;
    private final List<PageWithType> verificationSet;

    public Fold(List<PageWithType> trainingSet, List<PageWithType> verificationSet) {
        if (trainingSet == null) {
            throw new NullArgumentException("trainingSet");
        }
        if (verificationSet == null) {
            throw new NullArgumentException("verificationSet");
        }

        this.trainingSet = trainingSet;
        this.verificationSet = verificationSet;
    }

    public List<PageWithType> getTrainingSet() {
        return trainingSet;
    }

    public List<PageWithType> getVerificationSet() {
        return verificationSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fold)) return false;

        Fold fold = (Fold) o;

        return CollectionUtils.isEqualCollection(trainingSet, fold.trainingSet)
                && CollectionUtils.isEqualCollection(verificationSet, fold.verificationSet);

    }

    @Override
    public int hashCode() {
        int result = listHashCode(trainingSet);
        result = 31 * result + listHashCode(verificationSet);
        return result;
    }

    private static int listHashCode(List<PageWithType> list) {
        final int primeNumber = 151;
        int result = 1298911; // initial value, also prime
        for (PageWithType e: list) {
            result = primeNumber * result + e.hashCode();
        }
        return result;
    }
}
