package com.wikia.classifier.classifiers.training;

import com.beust.jcommander.internal.Lists;
import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.ClassifierFactory;
import com.wikia.classifier.classifiers.exceptions.ClassifyException;
import com.wikia.classifier.classifiers.model.ClassRelevance;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import com.wikia.classifier.classifiers.model.PageWithType;
import junit.framework.Assert;
import org.apache.commons.lang.NullArgumentException;
import org.fest.assertions.Delta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CrossValidatingClassifierTrainerTest {
    @Test
    public void testTrainParallel() throws Exception {
        PageWithType page1 = new PageWithType() {{ setTitle("Test"); setWikiText("Wikitext"); setType("character"); }};
        PageWithType page2 = new PageWithType() {{ setTitle("Test2"); setWikiText("Wikitext"); setType("bar"); }};
        PageWithType page3 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};
        List<PageWithType> trainingSet = Lists.newArrayList(page1, page2, page3);

        Fold fold1 = new Fold(Lists.newArrayList(page1), Lists.newArrayList(page2, page3));
        Fold fold2 = new Fold(Lists.newArrayList(page2, page3), Lists.newArrayList(page1));
        List<Fold> folds = Lists.newArrayList(fold1, fold2);

        // we will create classifier for every fold for cross verification.
        Classifier fold1Classifier = mock(Classifier.class);
        Classifier fold2Classifier = mock(Classifier.class);
        // and one final classifier as final result
        Classifier outputClassifier = mock(Classifier.class);

        ClassifierFactory classifierFactory = mock(ClassifierFactory.class);
        when(classifierFactory.build(fold1.getTrainingSet())).thenReturn(fold1Classifier); // train first fold
        when(classifierFactory.build(fold2.getTrainingSet())).thenReturn(fold2Classifier); // train second fold
        when(classifierFactory.build(trainingSet)).thenReturn(outputClassifier);           // train result classifier

        ClassifierVerifier verifierMock = mock(ClassifierVerifier.class);
        when(verifierMock.verify(fold1Classifier, fold1.getVerificationSet())).thenReturn(
                new ClassifierTrainingResult(fold1Classifier, Lists.newArrayList(
                        new ClassifierTrainingResult.ClassificationResultPair(page2, new ClassificationResult("bar", Lists.<ClassRelevance>newArrayList())),
                        new ClassifierTrainingResult.ClassificationResultPair(page3, new ClassificationResult("wrong-type", Lists.<ClassRelevance>newArrayList()))
                ), Lists.<ClassifyException>newArrayList()));
        when(verifierMock.verify(fold2Classifier, fold2.getVerificationSet())).thenReturn(
                new ClassifierTrainingResult(fold1Classifier, Lists.newArrayList(
                        new ClassifierTrainingResult.ClassificationResultPair(page1, new ClassificationResult("character", Lists.<ClassRelevance>newArrayList()))
                ), Lists.<ClassifyException>newArrayList()));

        FoldingStrategy foldingStrategy = mock(FoldingStrategy.class);
        when(foldingStrategy.fold(trainingSet)).thenReturn(folds);

        CrossValidatingClassifierTrainer crossValidatingClassifierTrainer = new CrossValidatingClassifierTrainer(classifierFactory, verifierMock, foldingStrategy, Executors.newFixedThreadPool(3));
        ClassifierTrainingResult classifierTrainingResult = crossValidatingClassifierTrainer.train(trainingSet);
        assertThat(classifierTrainingResult).isNotNull();
        assertThat(classifierTrainingResult.getSuccessRate()).isEqualTo(0.66666, Delta.delta(0.01));
        assertThat(classifierTrainingResult.getClassifier()).isEqualTo(outputClassifier);
    }
    @Test
    public void testTrain() throws Exception {
        PageWithType page1 = new PageWithType() {{ setTitle("Test"); setWikiText("Wikitext"); setType("character"); }};
        PageWithType page2 = new PageWithType() {{ setTitle("Test2"); setWikiText("Wikitext"); setType("bar"); }};
        PageWithType page3 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};
        List<PageWithType> trainingSet = Lists.newArrayList(page1, page2, page3);

        Fold fold1 = new Fold(Lists.newArrayList(page1), Lists.newArrayList(page2, page3));
        Fold fold2 = new Fold(Lists.newArrayList(page2, page3), Lists.newArrayList(page1));
        List<Fold> folds = Lists.newArrayList(fold1, fold2);

        // we will create classifier for every fold for cross verification.
        Classifier fold1Classifier = mock(Classifier.class);
        Classifier fold2Classifier = mock(Classifier.class);
        // and one final classifier as final result
        Classifier outputClassifier = mock(Classifier.class);

        ClassifierFactory classifierFactory = mock(ClassifierFactory.class);
        when(classifierFactory.build(fold1.getTrainingSet())).thenReturn(fold1Classifier); // train first fold
        when(classifierFactory.build(fold2.getTrainingSet())).thenReturn(fold2Classifier); // train second fold
        when(classifierFactory.build(trainingSet)).thenReturn(outputClassifier);           // train result classifier

        ClassifierVerifier verifierMock = mock(ClassifierVerifier.class);
        when(verifierMock.verify(fold1Classifier, fold1.getVerificationSet())).thenReturn(
                new ClassifierTrainingResult(fold1Classifier, Lists.newArrayList(
                        new ClassifierTrainingResult.ClassificationResultPair(page2, new ClassificationResult("bar", Lists.<ClassRelevance>newArrayList())),
                        new ClassifierTrainingResult.ClassificationResultPair(page3, new ClassificationResult("wrong-type", Lists.<ClassRelevance>newArrayList()))
                ), Lists.<ClassifyException>newArrayList()));
        when(verifierMock.verify(fold2Classifier, fold2.getVerificationSet())).thenReturn(
                new ClassifierTrainingResult(fold1Classifier, Lists.newArrayList(
                        new ClassifierTrainingResult.ClassificationResultPair(page1, new ClassificationResult("character", Lists.<ClassRelevance>newArrayList()))
                ), Lists.<ClassifyException>newArrayList()));

        FoldingStrategy foldingStrategy = mock(FoldingStrategy.class);
        when(foldingStrategy.fold(trainingSet)).thenReturn(folds);

        CrossValidatingClassifierTrainer crossValidatingClassifierTrainer = new CrossValidatingClassifierTrainer(classifierFactory, verifierMock, foldingStrategy, Executors.newSingleThreadExecutor());
        ClassifierTrainingResult classifierTrainingResult = crossValidatingClassifierTrainer.train(trainingSet);
        assertThat(classifierTrainingResult).isNotNull();
        assertThat(classifierTrainingResult.getSuccessRate()).isEqualTo(0.66666, Delta.delta(0.01));
        assertThat(classifierTrainingResult.getClassifier()).isEqualTo(outputClassifier);
    }

    @Test
    public void testTrainWithErrors() throws Exception {
        ClassifyException error1 = mock(ClassifyException.class);
        ClassifyException error2 = mock(ClassifyException.class);

        PageWithType page1 = new PageWithType() {{ setTitle("Test"); setWikiText("Wikitext"); setType("character"); }};
        PageWithType page2 = new PageWithType() {{ setTitle("Test2"); setWikiText("Wikitext"); setType("bar"); }};
        PageWithType page3 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};
        List<PageWithType> trainingSet = Lists.newArrayList(page1, page2, page3);

        Fold fold1 = new Fold(Lists.newArrayList(page1), Lists.newArrayList(page2, page3));
        Fold fold2 = new Fold(Lists.newArrayList(page2, page3), Lists.newArrayList(page1));
        List<Fold> folds = Lists.newArrayList(fold1, fold2);

        // we will create classifier for every fold for cross verification.
        Classifier fold1Classifier = mock(Classifier.class);
        Classifier fold2Classifier = mock(Classifier.class);
        // and one final classifier as final result
        Classifier outputClassifier = mock(Classifier.class);

        ClassifierFactory classifierFactory = mock(ClassifierFactory.class);
        when(classifierFactory.build(fold1.getTrainingSet())).thenReturn(fold1Classifier); // train first fold
        when(classifierFactory.build(fold2.getTrainingSet())).thenReturn(fold2Classifier); // train second fold
        when(classifierFactory.build(trainingSet)).thenReturn(outputClassifier);           // train result classifier

        ClassifierVerifier verifierMock = mock(ClassifierVerifier.class);
        when(verifierMock.verify(fold1Classifier, fold1.getVerificationSet())).thenReturn(
                new ClassifierTrainingResult(fold1Classifier, Lists.newArrayList(
                        new ClassifierTrainingResult.ClassificationResultPair(page2, new ClassificationResult("bar", Lists.<ClassRelevance>newArrayList())),
                        new ClassifierTrainingResult.ClassificationResultPair(page3, new ClassificationResult("foo", Lists.<ClassRelevance>newArrayList()))
                ), Lists.<ClassifyException>newArrayList(error1)));
        when(verifierMock.verify(fold2Classifier, fold2.getVerificationSet())).thenReturn(
                new ClassifierTrainingResult(fold1Classifier, Lists.newArrayList(
                        new ClassifierTrainingResult.ClassificationResultPair(page1, new ClassificationResult("character", Lists.<ClassRelevance>newArrayList()))
                ), Lists.<ClassifyException>newArrayList(error2)));

        FoldingStrategy foldingStrategy = mock(FoldingStrategy.class);
        when(foldingStrategy.fold(trainingSet)).thenReturn(folds);

        CrossValidatingClassifierTrainer crossValidatingClassifierTrainer = new CrossValidatingClassifierTrainer(classifierFactory, verifierMock, foldingStrategy, Executors.newSingleThreadExecutor());
        ClassifierTrainingResult classifierTrainingResult = crossValidatingClassifierTrainer.train(trainingSet);
        assertThat(classifierTrainingResult).isNotNull();
        assertThat(classifierTrainingResult.getSuccessRate()).isEqualTo(1, Delta.delta(0.01));
        assertThat(classifierTrainingResult.getNonFatalErrors()).hasSize(2);
        assertThat(classifierTrainingResult.getNonFatalErrors()).contains(error1);
        assertThat(classifierTrainingResult.getNonFatalErrors()).contains(error2);
    }


    @Test
    public void testTrainNullArgument() throws Exception {
        CrossValidatingClassifierTrainer crossValidatingClassifierTrainer = new CrossValidatingClassifierTrainer(
                mock(ClassifierFactory.class),
                mock(ClassifierVerifier.class),
                mock(FoldingStrategy.class),
                mock(ExecutorService.class));
        try {
            crossValidatingClassifierTrainer.train(null);
            Assert.fail("Should throw null argument exception.");
        } catch (NullArgumentException e) {

        }
    }
    @Test
    public void testTrainEmptyTrainingSet() throws Exception {
        CrossValidatingClassifierTrainer crossValidatingClassifierTrainer = new CrossValidatingClassifierTrainer(
                mock(ClassifierFactory.class),
                mock(ClassifierVerifier.class),
                mock(FoldingStrategy.class),
                mock(ExecutorService.class));
        try {
            crossValidatingClassifierTrainer.train(Lists.<PageWithType>newArrayList());
            Assert.fail("Should throw argument exception.");
        } catch (IllegalArgumentException e) {

        }
    }
}
