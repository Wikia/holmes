package com.wikia.classifier.classifiers.training;

import com.beust.jcommander.internal.Lists;
import com.wikia.classifier.classifiers.model.PageWithType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class FoldingStrategyImplTest {

    @Test
    public void testFold4Pages3Folds() throws Exception {
        PageWithType page1 = new PageWithType() {{ setTitle("Test"); setWikiText("Wikitext"); setType("character"); }};
        PageWithType page2 = new PageWithType() {{ setTitle("Test2"); setWikiText("Wikitext"); setType("bar"); }};
        PageWithType page3 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};
        PageWithType page4 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};

        FoldingStrategyImpl foldingStrategy = new FoldingStrategyImpl(3);
        List<Fold> fold = foldingStrategy.fold(Lists.newArrayList(page1, page2, page3, page4));
        assertThat(fold).isNotNull();
        assertThat(fold).hasSize(3);

        // larger verification sets go first
        assertThat(fold.get(0).getTrainingSet()).hasSize(2);
        assertThat(fold.get(0).getVerificationSet()).hasSize(2);

        assertThat(fold.get(1).getTrainingSet()).hasSize(3);
        assertThat(fold.get(1).getVerificationSet()).hasSize(1);

        assertThat(fold.get(2).getTrainingSet()).hasSize(3);
        assertThat(fold.get(2).getVerificationSet()).hasSize(1);

        checkFoldsInvariants(fold);
    }

    @Test
    public void testFold3Pages3Folds() throws Exception {
        PageWithType page1 = new PageWithType() {{ setTitle("Test"); setWikiText("Wikitext"); setType("character"); }};
        PageWithType page2 = new PageWithType() {{ setTitle("Test2"); setWikiText("Wikitext"); setType("bar"); }};
        PageWithType page3 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};

        FoldingStrategyImpl foldingStrategy = new FoldingStrategyImpl(3);
        List<Fold> fold = foldingStrategy.fold(Lists.newArrayList(page1, page2, page3));
        assertThat(fold).isNotNull();
        assertThat(fold).hasSize(3);

        assertThat(fold.get(0).getTrainingSet()).hasSize(2);
        assertThat(fold.get(0).getVerificationSet()).hasSize(1);

        assertThat(fold.get(1).getTrainingSet()).hasSize(2);
        assertThat(fold.get(1).getVerificationSet()).hasSize(1);

        assertThat(fold.get(2).getTrainingSet()).hasSize(2);
        assertThat(fold.get(2).getVerificationSet()).hasSize(1);

        checkFoldsInvariants(fold);
    }

    @Test
    public void testFold3Pages() throws Exception {
        PageWithType page1 = new PageWithType() {{ setTitle("Test"); setWikiText("Wikitext"); setType("character"); }};
        PageWithType page2 = new PageWithType() {{ setTitle("Test2"); setWikiText("Wikitext"); setType("bar"); }};
        PageWithType page3 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};

        FoldingStrategyImpl foldingStrategy = new FoldingStrategyImpl(2);
        List<Fold> fold = foldingStrategy.fold(Lists.newArrayList(page1, page2, page3));
        assertThat(fold).isNotNull();
        assertThat(fold).hasSize(2);

        // larger verification sets go first.
        assertThat(fold.get(0).getTrainingSet()).hasSize(1);
        assertThat(fold.get(0).getVerificationSet()).hasSize(2);

        assertThat(fold.get(1).getTrainingSet()).hasSize(2);
        assertThat(fold.get(1).getVerificationSet()).hasSize(1);

        checkFoldsInvariants(fold);
    }

    @Test
    public void testFold2Page() throws Exception {
        PageWithType page1 = new PageWithType() {{ setTitle("Test"); setWikiText("Wikitext"); setType("character"); }};
        PageWithType page2 = new PageWithType() {{ setTitle("Test2"); setWikiText("Wikitext"); setType("bar"); }};

        FoldingStrategyImpl foldingStrategy = new FoldingStrategyImpl(2);
        List<Fold> fold = foldingStrategy.fold(Lists.newArrayList(page1, page2));
        assertThat(fold).isNotNull();
        assertThat(fold).hasSize(2);

        assertThat(fold.get(0).getTrainingSet()).hasSize(1);
        assertThat(fold.get(0).getVerificationSet()).hasSize(1);

        assertThat(fold.get(1).getTrainingSet()).hasSize(1);
        assertThat(fold.get(1).getVerificationSet()).hasSize(1);

        checkFoldsInvariants(fold);
    }

    private static void checkFoldsInvariants(List<Fold> folds) {
        // verify that for every folds verification set is contained by every others fold training set
        for(Fold verificationFold: folds) {
            for (Fold trainingSetFold: folds) {
                if (verificationFold != verificationFold) {
                    for (PageWithType verificationFoldElement: verificationFold.getVerificationSet()) {
                        assertThat(trainingSetFold.getTrainingSet()).contains(verificationFoldElement);
                    }
                }
            }
        }
    }

    @Test
    public void testFoldThrowOnOneFold() throws Exception {
        try {
            new FoldingStrategyImpl(1);
            Assert.fail("Should throw if number of folds is one. Number of folds lower than 2 makes no sense.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).contains("folds");
        }
    }

    @Test
    public void testFoldThrowOnZeroFold() throws Exception {
        try {
            new FoldingStrategyImpl(0);
            Assert.fail("Should throw if number of folds is zero. Number of folds lower than 2 makes no sense.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).contains("folds");
        }
    }
}
