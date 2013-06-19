package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 13.04.13
 * Time: 19:09
 */

import com.wikia.reader.filters.CollectionFilterBase;
import com.wikia.reader.input.structured.WikiPageSection;
import com.wikia.reader.input.structured.WikiPageStructure;
import com.wikia.reader.matrix.SparseMatrix;
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
