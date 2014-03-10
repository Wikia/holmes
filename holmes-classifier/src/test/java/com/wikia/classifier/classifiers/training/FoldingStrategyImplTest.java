package com.wikia.classifier.classifiers.training;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.wikia.classifier.classifiers.model.PageWithType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class FoldingStrategyImplTest {

    @Test
    public void testSeed() {
        FoldingStrategyImpl foldingStrategy = new FoldingStrategyImpl(3);
        assertThat(foldingStrategy.getSeed()).isEqualTo(1); // default value is 1
        foldingStrategy.setSeed(13);
        assertThat(foldingStrategy.getSeed()).isEqualTo(13);
    }

    @Test
    public void testFoldingStrategyIsDeterministic() {
        PageWithType page1 = new PageWithType() {{ setTitle("Test"); setWikiText("Wikitext"); setType("character"); }};
        PageWithType page2 = new PageWithType() {{ setTitle("Test2"); setWikiText("Wikitext"); setType("bar"); }};
        PageWithType page3 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};
        PageWithType page4 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};

        FoldingStrategyImpl foldingStrategy = new FoldingStrategyImpl(3);
        List<Fold> folds1 = foldingStrategy.fold(Lists.newArrayList(page1, page2, page3, page4));
        List<Fold> folds2 = foldingStrategy.fold(Lists.newArrayList(page1, page2, page3, page4));

        for (int i=0; i<folds1.size(); i++) {
            assertThat(folds1.get(i).getTrainingSet()).isEqualTo(folds2.get(i).getTrainingSet());
            assertThat(folds1.get(i).getVerificationSet()).isEqualTo(folds2.get(i).getVerificationSet());
        }
        // same as above
        assertThat(folds1).isEqualTo(folds2);
    }

    @Test
    public void testFoldingStrategyOutputIsDependentOnSeed() {
        PageWithType page1 = new PageWithType() {{ setTitle("Test"); setWikiText("Wikitext"); setType("character"); }};
        PageWithType page2 = new PageWithType() {{ setTitle("Test2"); setWikiText("Wikitext"); setType("bar"); }};
        PageWithType page3 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};
        PageWithType page4 = new PageWithType() {{ setTitle("Test3"); setWikiText("Wikitext"); setType("foo"); }};

        FoldingStrategyImpl foldingStrategy = new FoldingStrategyImpl(3);
        List<Fold> folds1 = foldingStrategy.fold(Lists.newArrayList(page1, page2, page3, page4));
        foldingStrategy.setSeed(1324);
        List<Fold> folds2 = foldingStrategy.fold(Lists.newArrayList(page1, page2, page3, page4));

        assertThat(folds1).isNotEqualTo(folds2);
    }

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

    @Test
    public void testGetTotalFolds() throws Exception {
        assertThat(new FoldingStrategyImpl(13).getTotalFolds()).isEqualTo(13);
    }

    private static void checkFoldsInvariants(List<Fold> folds) {
        // verify that for every folds verification set is contained by every others fold training set
        int verificationSetIndex = 0;
        for(Fold verificationFold: folds) {
            int trainingSetIndex = 0;
            for (Fold trainingSetFold: folds) {
                if (trainingSetFold != verificationFold) {
                    for (PageWithType verificationFoldElement: verificationFold.getVerificationSet()) {
                        String trainingSetElements = Iterables.toString(
                                Iterables.transform(trainingSetFold.getTrainingSet(), new Function<PageWithType, String>() {
                                    @Override
                                    public String apply(PageWithType input) {
                                        return input.getTitle();
                                    }
                                }));
                        assertThat(trainingSetFold.getTrainingSet())
                                .overridingErrorMessage(String.format("%s from verification set %d is not in training set no %d (%s).",
                                        verificationFoldElement.getTitle()
                                        , verificationSetIndex
                                        , trainingSetIndex
                                        , trainingSetElements))
                                        .contains(verificationFoldElement);
                    }
                }
                trainingSetIndex++;
            }
            verificationSetIndex++;
        }

        for (Fold fold :folds) {
            // No element from verification set should be contained in training set
            for (PageWithType verificationElement: fold.getVerificationSet()) {
                assertThat(verificationElement).isNotIn(fold.getTrainingSet());
            }
            // No element from training set should be contained in verification set
            for (PageWithType trainingElement: fold.getTrainingSet()) {
                assertThat(trainingElement).isNotIn(fold.getVerificationSet());
            }
        }
    }
}
