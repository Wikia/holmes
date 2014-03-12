package com.wikia.classifier.filters.text;

import com.wikia.classifier.wikitext.WikiPageCategory;
import com.wikia.classifier.wikitext.WikiPageFeatures;

public class ExtractCategoryNGramsFilter extends NGramFilterBase {

    public ExtractCategoryNGramsFilter(int n) {
        super(n, String.format("cat_%d:", n));
    }

    @Override
    protected String getTextSource(WikiPageFeatures wikiPageFeatures) {
        StringBuilder stringBuilder = new StringBuilder();
        for(WikiPageCategory wikiPageCategory: wikiPageFeatures.getCategories()) {
            stringBuilder.append(wikiPageCategory.getTitle());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
