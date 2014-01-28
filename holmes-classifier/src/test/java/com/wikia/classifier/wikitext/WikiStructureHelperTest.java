package com.wikia.classifier.wikitext;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class WikiStructureHelperTest {

    @Test
    public void testParse() throws Exception {
        assertThat( WikiStructureHelper.parse("Title", "wikiText") ).isNotNull();
        assertThat( WikiStructureHelper.parse("Title", "wikiText").getTitle() ).isSameAs("Title");
        assertThat( WikiStructureHelper.parse("Title", "wikiText").getPlain() ).contains("wikiText");
    }
}
