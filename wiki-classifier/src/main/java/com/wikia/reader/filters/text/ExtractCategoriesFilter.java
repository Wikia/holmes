package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 22:58
 */

import com.wikia.reader.filters.CollectionFilterBase;
import com.wikia.reader.input.structured.WikiPageCategory;
import com.wikia.reader.input.structured.WikiPageStructure;
import com.wikia.reader.input.structured.WikiPageTemplateArgument;
import com.wikia.reader.text.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ExtractCategoriesFilter extends CollectionFilterBase<WikiPageStructure, SparseMatrix> {
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
                matrix.put(wikiPageStructure.getTitle(), "cat:"+name, 1);
            }
        }
        return matrix;
    }
}
