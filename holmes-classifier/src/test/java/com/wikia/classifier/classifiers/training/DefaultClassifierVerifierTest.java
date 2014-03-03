package com.wikia.classifier.classifiers.training;

import com.google.common.collect.Lists;
import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.model.ClassRelevance;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import com.wikia.classifier.classifiers.model.PageWithType;
import junit.framework.Assert;
import org.apache.commons.lang.NullArgumentException;
import org.fest.assertions.Delta;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultClassifierVerifierTest {

    @Test
    public void testVerify() throws Exception {
        PageWithType page1 = new PageWithType() {{ setTitle("Test"); setWikiText("Wikitext"); setType("character"); }};
        PageWithType page2 = new PageWithType() {{ setTitle("Test2"); setWikiText("Wikitext"); setType("bar"); }};
        PageWithType page3 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};

        ClassificationResult result1 = new ClassificationResult("character", Lists.<ClassRelevance>newArrayList());
        ClassificationResult result2 = new ClassificationResult("bad_type", Lists.<ClassRelevance>newArrayList());
        ClassificationResult result3 = new ClassificationResult("foo", Lists.<ClassRelevance>newArrayList());

        Classifier classifier = mock(Classifier.class);
        when(classifier.classify(page1)).thenReturn(result1);
        when(classifier.classify(page2)).thenReturn(result2);
        when(classifier.classify(page3)).thenReturn(result3);

        DefaultClassifierVerifier subject = new DefaultClassifierVerifier();
        ClassifierTrainingResult verify = subject.verify(classifier, Lists.newArrayList(page1, page2, page3));

        assertThat(verify.getClassifier()).isEqualTo(classifier);
        assertThat(verify.getSuccessRate()).isEqualTo(0.66666, Delta.delta(0.01));
        assertThat(verify.getClassificationResultPairList().get(1).getResult().getSingleClass()).isEqualTo("bad_type");
        assertThat(verify.getClassificationResultPairList().get(1).getPage().getType()).isEqualTo("bar");
    }

    @Test
    public void testNullClassifier() {
        try {
            DefaultClassifierVerifier subject = new DefaultClassifierVerifier();
            subject.verify(null, Lists.<PageWithType>newArrayList());
            Assert.fail("should throw on null classifier");
        } catch (NullArgumentException e) {
            assertThat(e.getMessage()).contains("classifier");
        }
    }

    @Test
    public void testNullVerificationSet() {
        try {
            DefaultClassifierVerifier subject = new DefaultClassifierVerifier();
            subject.verify(mock(Classifier.class), null);
            Assert.fail("should throw on null verificationSet");
        } catch (NullArgumentException e) {
            assertThat(e.getMessage()).contains("verificationSet");
        }
    }

    @Test
    public void testEmptyVerificationSet() {
        try {
            DefaultClassifierVerifier subject = new DefaultClassifierVerifier();
            subject.verify(mock(Classifier.class), Lists.<PageWithType>newArrayList());
            Assert.fail("should throw on empty verificationSet");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).contains("verificationSet");
        }
    }
}
