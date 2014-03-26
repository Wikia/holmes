package com.wikia.classifier.wikitext;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class WikiTextFeaturesHelperTest {

    @Test
    public void testParse() throws Exception {
        assertThat( WikiTextFeaturesHelper.parse("Title", "wikiText") ).isNotNull();
        assertThat( WikiTextFeaturesHelper.parse("Title", "wikiText").getTitle() ).isSameAs("Title");
        assertThat( WikiTextFeaturesHelper.parse("Title", "wikiText").getPlain() ).contains("wikiText");
    }

    @Test
    public void testParseBold() throws Exception {
        String text = "''' foo ''' bar";

        assertThat(WikiTextFeaturesHelper.parse("Title", text)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", text).getPlain()).contains("foo");
        assertThat(WikiTextFeaturesHelper.parse("Title", text).getPlain()).contains("bar");
    }

    @Test
    public void testParseBoldBroken() throws Exception {
        String text = "''' foo ' bar";

        assertThat(WikiTextFeaturesHelper.parse("Title", text)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", text).getPlain()).contains("foo");
        assertThat(WikiTextFeaturesHelper.parse("Title", text).getPlain()).contains("bar");
    }

    @Test
    public void testParseHtmlTable() throws Exception {
        String input = "<table style=\"width:300px\">\n" +
                "<tr>\n" +
                "  <td>Jill</td>\n" +
                "  <td>Smith</td> \n" +
                "  <td>50</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "  <td><b>Eve<b/></td>\n" +
                "  <td>Jackson</td> \n" +
                "  <td>94</td>\n" +
                "</tr>\n" +
                "</table>";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains("Jill");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains("Eve");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("table");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("width");
    }

    @Test
    public void testParseHtmlTableBroken() throws Exception {
        String input = "<table style=\"width:300px\">\n" +
                "<tr>\n" +
                "  <td>Jill</td>\n" +
                "  <td>Smith</td> \n" +
                "  <td>50</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "  <td><b>Eve<b/></td>\n" +
                "  <td>Jackson</td> \n" +
                "  <td>94</td>\n" +
                "</tr>\n" +
                "</tabl";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains("Jill");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains("Eve");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("table");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("width");
    }

    @Test
    public void testParseHtmlTableBrokenMissingEndTag() throws Exception {
        String input = "<table style=\"width:300px\">\n" +
                "<tr>\n" +
                "  <td>Jill</td>\n" +
                "  <td>Smith</td> \n" +
                "  <td>50</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "  <td><b>Eve<b/></td>\n" +
                "  <td>Jackson</td> \n" +
                "  <td>94</td>\n" +
                "</tr>";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains("Jill");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains("Eve");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("table");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("width");
    }

    @Test
    public void testParseHtmlDiv() throws Exception {
        String input = "<div style=\"asdf\" class=\"cl\">Jil</div>";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains("Jil");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("div");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("cl");
    }

    @Test
    public void testParseHtmlDivBroken() throws Exception {
        String input = "<div style=\"asdf\" class=\"cl\">Jil</di";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains("Jil");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("div");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("cl");
    }

    @Test
    public void testParseHtmlDivBrokenMissingEndTag() throws Exception {
        String input = "<div style=\"asdf\" class=\"cl\">Jil";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains("Jil");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("div");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).doesNotContain("cl");
    }

    @Test
    public void testParseCategories() throws Exception {
        String input = "[[Category:Fun People|Einstein, Albert]] [[Category:Stubs]]";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getCategories()).hasSize(2);
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getCategories().get(0).getTitle()).isEqualTo("Fun People");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getCategories().get(1).getTitle()).isEqualTo("Stubs");
    }

    @Test
    public void testParseCategoriesBrokenUnclosedCategory() throws Exception {
        String input = "[[Category:Fun People|Einstein, Albert]] [[Category:Stubs]";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getCategories()).hasSize(1);
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getCategories().get(0).getTitle()).isEqualTo("Fun People");
    }

    @Test
    public void testParseCategoriesBrokenUnclosedCategory2() throws Exception {
        String input = "[[Category:Fun People|Einstein, Albert]] [[Category:Stubs]";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getCategories()).hasSize(1);
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getCategories().get(0).getTitle()).isEqualTo("Fun People");
    }

    @Test
    public void testParseSections() throws Exception {
        String input = "summary\n" +
                "== Section ==\n" +
                "some content\n" +
                "some more content\n" +
                "=== Section 3 ===\n" +
                "\n" +
                "asd";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getSections()).hasSize(2);
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getSections().get(0).getTitle()).isEqualTo("Section");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getSections().get(1).getTitle()).isEqualTo("Section 3");
    }

    @Test
    public void testParseSectionsBroken() throws Exception {
        String input = "summary\n" +
                "== Section ==\n" +
                "some content\n" +
                "some more content\n" +
                "=== Section 3 =\n" +
                "\n" +
                "asd";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getSections()).hasSize(2);
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getSections().get(0).getTitle()).isEqualTo("Section");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getSections().get(1).getTitle()).isEqualTo("== Section 3");
    }


    @Test
    public void testParseTemplates() throws Exception {
        String input = "{{templatename|parvalue1|parvalue2}}";

        assertThat(WikiTextFeaturesHelper.parse("Title", input)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getTemplates()).hasSize(1);
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getTemplates().get(0).getName()).isEqualTo("templatename");
    }

    @Test
    public void testParseTemplateArguments() throws Exception {
        String input = "foo\n" +
                "{{templatename|paramname1=parvalue1|paramname2=parvalue2}}\n" +
                "xxx";

        assertThat(WikiTextFeaturesHelper.parse("Title", input).getTemplateArguments()).hasSize(2);
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getTemplateArguments().get(0).getName()).isEqualTo("paramname1");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getTemplateArguments().get(0).getStringValue()).isEqualTo("parvalue1");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getTemplateArguments().get(1).getName()).isEqualTo("paramname2");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getTemplateArguments().get(1).getStringValue()).isEqualTo("parvalue2");
    }

    @Test
    public void testParseLinks() throws Exception {
        String input = "foo\n" +
                "a:[[Page Title1|display title]]\n" +
                "b: [[Page Title2]]\n" +
                "xxx";

        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains("display title");

        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains(" Page Title1");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getPlain()).contains(" Page Title2");

        assertThat(WikiTextFeaturesHelper.parse("Title", input).getInternalLinks()).hasSize(2);
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getInternalLinks().get(0).getTitle()).isEqualTo("display title");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getInternalLinks().get(0).getTo()).isEqualTo("Page Title1");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getInternalLinks().get(1).getTitle()).isEqualTo("Page Title2");
        assertThat(WikiTextFeaturesHelper.parse("Title", input).getInternalLinks().get(1).getTo()).isEqualTo("Page Title2");
    }

    @Test
    public void testParseTable() throws Exception {
        String table = "{| border=1\n" +
                "  |+ Caption\n" +
                "|-\n" +
                "  ! Heading 1\n" +
                "  ! Heading 2\n" +
                "|-\n" +
                "  | fooo\n" +
                "  | B\n" +
                "|-\n" +
                "  | C\n" +
                "  | '''bold'''\n" +
                "|}";

        assertThat(WikiTextFeaturesHelper.parse("Title", table)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", table).getPlain()).contains("Heading 1");
        assertThat(WikiTextFeaturesHelper.parse("Title", table).getPlain()).contains("fooo");
        assertThat(WikiTextFeaturesHelper.parse("Title", table).getPlain()).contains("bold");
    }

    @Test
    public void testParseTableBroken2() throws Exception {
        String table = "{| border=1\n" +
                "  |+ Caption\n" +
                "|-\n" +
                "  ! Heading 1\n" +
                "  ! Heading 2\n" +
                "|-\n" +
                "  | fooo\n" +
                "  | B\n" +
                "|-\n" +
                "  | C\n" +
                "  | '''bold'''\n" +
                "";

        assertThat(WikiTextFeaturesHelper.parse("Title", table)).isNotNull();
        assertThat(WikiTextFeaturesHelper.parse("Title", table).getPlain()).contains("Heading 1");
        assertThat(WikiTextFeaturesHelper.parse("Title", table).getPlain()).contains("fooo");
        assertThat(WikiTextFeaturesHelper.parse("Title", table).getPlain()).contains("bold");
    }
}
