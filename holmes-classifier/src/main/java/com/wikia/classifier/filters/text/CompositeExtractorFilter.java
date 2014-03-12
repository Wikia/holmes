package com.wikia.classifier.filters.text;

import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.filters.Filter;
import com.wikia.classifier.wikitext.WikiPageFeatures;
import com.wikia.classifier.util.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class CompositeExtractorFilter extends CollectionFilterBase<WikiPageFeatures, SparseMatrix> {
    private static final long serialVersionUID = -4216220958359075039L;
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(CompositeExtractorFilter.class);
    private final List<Filter<Collection<WikiPageFeatures>, SparseMatrix>> filters;

    public CompositeExtractorFilter(List<Filter<Collection<WikiPageFeatures>, SparseMatrix>> filters) {
        super(WikiPageFeatures.class, SparseMatrix.class);
        this.filters = filters;
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageFeatures> params) {
        SparseMatrix ret= new SparseMatrix();
        for(Filter<Collection<WikiPageFeatures>, SparseMatrix> filter: filters) {
            SparseMatrix matrix = filter.filter(params);
            ret.merge(matrix);
        }
        return ret;
    }
}
