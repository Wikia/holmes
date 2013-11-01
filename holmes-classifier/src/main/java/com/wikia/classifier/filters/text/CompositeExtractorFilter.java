package com.wikia.classifier.filters.text;

import com.google.common.collect.Lists;
import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.filters.Filter;
import com.wikia.classifier.input.structured.WikiPageStructure;
import com.wikia.classifier.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class CompositeExtractorFilter extends CollectionFilterBase<WikiPageStructure, SparseMatrix> {
    private static final long serialVersionUID = -4216220958359075039L;
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(CompositeExtractorFilter.class);
    private final List<Filter<Collection<WikiPageStructure>, SparseMatrix>> filters;

    public CompositeExtractorFilter(Filter<Collection<WikiPageStructure>, SparseMatrix>...filters) {
        this(Lists.newArrayList(filters));
    }

    public CompositeExtractorFilter(List<Filter<Collection<WikiPageStructure>, SparseMatrix>> filters) {
        super(WikiPageStructure.class, SparseMatrix.class);
        this.filters = filters;
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageStructure> params) {
        SparseMatrix ret= new SparseMatrix();
        for(Filter<Collection<WikiPageStructure>, SparseMatrix> filter: filters) {
            SparseMatrix matrix = filter.filter(params);
            ret.merge(matrix);
        }
        return ret;
    }
}
