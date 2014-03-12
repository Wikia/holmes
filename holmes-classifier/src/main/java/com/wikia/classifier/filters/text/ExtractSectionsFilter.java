package com.wikia.classifier.filters.text;

import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.wikitext.WikiPageFeatures;
import com.wikia.classifier.wikitext.WikiPageSection;
import com.wikia.classifier.util.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ExtractSectionsFilter extends CollectionFilterBase<WikiPageFeatures, SparseMatrix> {
    private static final long serialVersionUID = 7179107473450821264L;
    private static Logger logger = LoggerFactory.getLogger(ExtractSectionsFilter.class);

    public ExtractSectionsFilter() {
        super(WikiPageFeatures.class, SparseMatrix.class);
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageFeatures> pages) {
        SparseMatrix matrix = new SparseMatrix();
        int i = 0;
        for(WikiPageFeatures structure:pages) {
            for(WikiPageSection section: structure.getSections()) {
                String name = section.getTitle();
                name = name.trim().toLowerCase();
                String key = "sec:"+name;
                logger.debug(key);
                matrix.put(String.valueOf(i), key, 1);
            }
            i++;
        }
        return matrix;
    }
}
