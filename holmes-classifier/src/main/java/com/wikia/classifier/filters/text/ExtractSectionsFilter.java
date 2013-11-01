package com.wikia.classifier.filters.text;

import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.input.structured.WikiPageSection;
import com.wikia.classifier.input.structured.WikiPageStructure;
import com.wikia.classifier.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ExtractSectionsFilter extends CollectionFilterBase<WikiPageStructure, SparseMatrix> {
    private static final long serialVersionUID = 7179107473450821264L;
    private static Logger logger = LoggerFactory.getLogger(ExtractSectionsFilter.class);

    public ExtractSectionsFilter() {
        super(WikiPageStructure.class, SparseMatrix.class);
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageStructure> pages) {
        SparseMatrix matrix = new SparseMatrix();
        for(WikiPageStructure structure:pages) {
            for(WikiPageSection section: structure.getSections()) {
                String name = section.getTitle();
                name = name.trim().toLowerCase();
                String key = "sec:"+name;
                logger.debug(key);
                matrix.put(structure.getTitle(), key, 1);
            }
        }
        return matrix;
    }
}
