package com.wikia.classifier.classifiers;

import com.wikia.api.model.Page;
import com.wikia.classifier.Resources;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import static org.fest.assertions.Assertions.assertThat;

public class ClassifierIT {
    private Page movie;

    @BeforeSuite
    public void initVariables() {
        movie = new Page();
        movie.setTitle("The Movie (movie)");
        movie.setWikiText("Movie about things \n" +
                "{{Infobox film}}\n" +
                "{{Infobox movie}}\n" +
                "==Synopsis==\n" +
                "==Plot==\n" +
                " ... [[Category:Movies]][[Category:Movie]][[Category:Film]]");
    }

    @Test
    public void testBuild() throws Exception {
        Classifier classifier = new DefaultClassifierFactory().build(Resources.getExampleSet());
        ClassificationResult result = classifier.classify(movie);

        assertThat( result ).isNotNull();
        assertThat( result.getSingleClass() ).isIn(Lists.newArrayList("other", "movie"));
    }
}
