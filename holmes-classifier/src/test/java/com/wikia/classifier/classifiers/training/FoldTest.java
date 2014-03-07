package com.wikia.classifier.classifiers.training;

import com.google.common.collect.Lists;
import com.wikia.classifier.classifiers.model.PageWithType;
import org.apache.commons.lang.NullArgumentException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class FoldTest {

    @Test
    public void testConstructorNullTrainingSet() throws Exception {
        try {
            new Fold(null, Lists.<PageWithType>newArrayList());
            Assert.fail("Should throw when trainingSet is null.");
        } catch (NullArgumentException e) {
        }
    }

    @Test
    public void testConstructorNullVerificationSet() throws Exception {
        try {
            new Fold(Lists.<PageWithType>newArrayList(), null);
            Assert.fail("Should throw when verificationSet is null.");
        } catch (NullArgumentException e) {
        }
    }

    @Test
    public void testGetTrainingSet() throws Exception {
        List<PageWithType> trainingSet = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(2l); }});
        List<PageWithType> verificationSet = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(13l); }});

        Fold fold = new Fold(trainingSet, verificationSet);

        assertThat(fold.getTrainingSet()).isEqualTo(trainingSet);
    }

    @Test
    public void testGetVerificationSet() throws Exception {
        List<PageWithType> trainingSet = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(2l); }});
        List<PageWithType> verificationSet = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(13l); }});

        Fold fold = new Fold(trainingSet, verificationSet);

        assertThat(fold.getVerificationSet()).isEqualTo(verificationSet);
    }

    @Test
    public void testEquals() throws Exception {
        List<PageWithType> trainingSet = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(2l); }});
        List<PageWithType> trainingSet2 = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(102l); }});
        List<PageWithType> verificationSet = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(13l); }});
        List<PageWithType> verificationSet2 = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(113l); }});

        Fold fold = new Fold(trainingSet, verificationSet);
        Fold fold2 = new Fold(trainingSet2, verificationSet2);

        assertThat(fold.equals(fold2)).isFalse();
        assertThat(fold.equals(new Fold(trainingSet, verificationSet2))).isFalse();
        assertThat(fold.equals(new Fold(trainingSet2, verificationSet))).isFalse();
        assertThat(fold.equals(new Fold(trainingSet2, verificationSet2))).isFalse();

        assertThat(fold2.equals(new Fold(trainingSet2, verificationSet2))).isTrue();
        assertThat(fold.equals(fold)).isTrue();
        assertThat(fold2.equals(fold2)).isTrue();
    }

    @Test
    public void testHashCode() throws Exception {
        List<PageWithType> trainingSet = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(2l); }});
        List<PageWithType> trainingSet2 = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(102l); }});
        List<PageWithType> verificationSet = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(13l); }});
        List<PageWithType> verificationSet2 = Lists.<PageWithType>newArrayList(new PageWithType() {{ setWikiId(113l); }});

        Fold fold = new Fold(trainingSet, verificationSet);
        Fold fold2 = new Fold(trainingSet2, verificationSet2);

        assertThat(fold.hashCode()).isNotEqualTo(fold2.hashCode());
        assertThat(fold.hashCode()).isNotEqualTo(new Fold(trainingSet, verificationSet2).hashCode());
        assertThat(fold.hashCode()).isNotEqualTo(new Fold(trainingSet2, verificationSet).hashCode());
        assertThat(fold.hashCode()).isNotEqualTo(new Fold(trainingSet2, verificationSet2).hashCode());

        assertThat(fold2.hashCode()).isEqualTo(new Fold(trainingSet2, verificationSet2).hashCode());
        assertThat(fold2.hashCode()).isEqualTo(fold2.hashCode());
        assertThat(fold.hashCode()).isEqualTo(fold.hashCode());
    }
}
