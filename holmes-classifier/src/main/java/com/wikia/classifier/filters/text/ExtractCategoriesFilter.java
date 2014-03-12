package com.wikia.classifier.filters.text;

import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.wikitext.WikiPageCategory;
import com.wikia.classifier.wikitext.WikiPageFeatures;
import com.wikia.classifier.util.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ExtractCategoriesFilter extends CollectionFilterBase<WikiPageFeatures, SparseMatrix> {
    private static final long serialVersionUID = -8070904770497835417L;
    private static Logger logger = LoggerFactory.getLogger(ExtractCategoriesFilter.class);

    public ExtractCategoriesFilter() {
        super(WikiPageFeatures.class, SparseMatrix.class);
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageFeatures> params) {
        SparseMatrix matrix = new SparseMatrix();
        int i = 0;
        for(WikiPageFeatures wikiPageFeatures : params) {
            for(WikiPageCategory category: wikiPageFeatures.getCategories()) {
                String name = category.getTitle();
                name = name.trim().toLowerCase();
                String key = "cat:"+name;
                logger.debug(key);
                matrix.put(String.valueOf(i), key, 1);
            }
            i++;
        }
        return matrix;
    }
}
