package com.wikia.classifier.classifiers.training;

import com.google.common.collect.Lists;
import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.ClassifierFactory;
import com.wikia.classifier.classifiers.exceptions.ClassifyException;
import com.wikia.classifier.classifiers.model.PageWithType;
import org.apache.commons.lang.NullArgumentException;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CrossValidatingClassifierTrainer implements ClassifierTrainer {
    private final ClassifierFactory classifierFactory;
    private final ClassifierVerifier verifier;
    private final FoldingStrategy foldingStrategy;
    private final ExecutorService executorService;

    public CrossValidatingClassifierTrainer(ClassifierFactory classifierFactory, ClassifierVerifier verifier, FoldingStrategy foldingStrategy, ExecutorService executorService) {
        this.classifierFactory = classifierFactory;
        this.verifier = verifier;
        this.foldingStrategy = foldingStrategy;
        this.executorService = executorService;
    }

    @Override
    public ClassifierTrainingResult train(final List<PageWithType> trainingSet) {
        if (trainingSet == null) {
            throw new NullArgumentException("trainingSet");
        }
        if (trainingSet.size() < 1) {
            throw new IllegalArgumentException("trainingSet should have at leas one element.");
        }
        List<Fold> folds = foldingStrategy.fold(trainingSet);
        List<Future<ClassifierTrainingResult>> foldResults = Lists.newArrayListWithCapacity(folds.size());
        Future<Classifier> outputClassifier = executorService.submit(new Callable<Classifier>() {
            @Override
            public Classifier call() throws Exception {
                return classifierFactory.build(trainingSet);
            }
        });
        for(final Fold fold: folds) {
            foldResults.add(executorService.submit(new Callable<ClassifierTrainingResult>() {
                @Override
                public ClassifierTrainingResult call() throws Exception {
                    Classifier foldClassifier = classifierFactory.build(fold.getTrainingSet());
                    return verifier.verify(foldClassifier, fold.getVerificationSet());
                }
            }));
        }
        List<ClassifierTrainingResult.ClassificationResultPair> results = Lists.newArrayListWithCapacity(trainingSet.size());
        List<ClassifyException> classificationErrors = Lists.newArrayList();
        for(Future<ClassifierTrainingResult> foldResult: foldResults) {
            try {
                ClassifierTrainingResult classifierTrainingResult = foldResult.get();
                results.addAll(classifierTrainingResult.getClassificationResultPairList());
                classificationErrors.addAll(classifierTrainingResult.getNonFatalErrors());
            } catch (InterruptedException e) {
                throw new RuntimeException("Classification was interrupted.", e);
            } catch (ExecutionException e) {
                throw new RuntimeException("Exception occurred while training classifier.", e);
            }
        }
        try {
            return new ClassifierTrainingResult(outputClassifier.get(), results, classificationErrors);
        } catch (InterruptedException e) {
            throw new RuntimeException("Classification was interrupted.", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Exception occurred while training classifier.", e);
        }
    }
}
