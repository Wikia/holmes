package com.wikia.classifier.filters.text;

import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.input.structured.WikiPageCategory;
import com.wikia.classifier.input.structured.WikiPageStructure;
import com.wikia.classifier.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ExtractCategoriesFilter extends CollectionFilterBase<WikiPageStructure, SparseMatrix> {
    private static final long serialVersionUID = -8070904770497835417L;
    private static Logger logger = LoggerFactory.getLogger(ExtractCategoriesFilter.class);

    public ExtractCategoriesFilter() {
        super(WikiPageStructure.class, SparseMatrix.class);
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageStructure> params) {

        SparseMatrix matrix = new SparseMatrix();
        for(WikiPageStructure wikiPageStructure: params) {
            for(WikiPageCategory category: wikiPageStructure.getCategories()) {
                String name = category.getTitle();
                name = name.trim().toLowerCase();
                String key = "cat:"+name;
                logger.debug(key);
                matrix.put(wikiPageStructure.getTitle(), key, 1);
            }
        }
        return matrix;
    }
}
