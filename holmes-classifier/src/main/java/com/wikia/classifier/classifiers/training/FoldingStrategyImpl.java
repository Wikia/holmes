package com.wikia.classifier.classifiers.training;

import com.google.common.collect.Lists;
import com.wikia.classifier.classifiers.model.PageWithType;

import java.util.Collections;
import java.util.List;

public class FoldingStrategyImpl implements FoldingStrategy {
    private final int totalFolds;

    public FoldingStrategyImpl(int totalFolds) {
        if (totalFolds < 2) {
            throw new IllegalArgumentException("Number of folds cannot be smaller than 2.");
        }
        this.totalFolds = totalFolds;
    }

    @Override
    public List<Fold> fold(List<PageWithType> originalSet) {
        List<PageWithType> randomizedSet = Lists.newArrayList(originalSet);
        Collections.shuffle(randomizedSet);

        List<Fold> folds = Lists.newArrayListWithCapacity(totalFolds);
        int minFoldSize = originalSet.size() / totalFolds;
        int numberOfLargerFolds = originalSet.size() % totalFolds;
        int startingPosition = 0;
        for (int i=0; i<totalFolds; i++) {
            int verificationSetSize = (i<numberOfLargerFolds) ? (minFoldSize+1) : (minFoldSize);
            List<PageWithType> verificationSet = randomizedSet.subList(startingPosition, startingPosition+verificationSetSize);
            List<PageWithType> trainingSetPrefix = randomizedSet.subList(0, startingPosition);
            List<PageWithType> trainingSetSuffix = randomizedSet.subList(startingPosition+verificationSetSize, randomizedSet.size());
            List<PageWithType> trainingSet = Lists.newArrayListWithCapacity(trainingSetPrefix.size() + trainingSetSuffix.size());
            trainingSet.addAll(trainingSetPrefix);
            trainingSet.addAll(trainingSetSuffix);
            folds.add(new Fold(trainingSet, verificationSet));
        }
        return folds;
    }

    public int getTotalFolds() {
        return totalFolds;
    }
}
