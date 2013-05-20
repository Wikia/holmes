package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 12.04.13
 * Time: 10:10
 */

import com.wikia.reader.filters.CollectionFilterBase;
import com.wikia.reader.filters.Filter;
import com.wikia.reader.text.data.InstanceSource;
import com.wikia.reader.text.matrix.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AnnotateMatrixFilter extends CollectionFilterBase<InstanceSource, Matrix> {
    private static Logger logger = LoggerFactory.getLogger(AnnotateMatrixFilter.class);
    private Filter<Collection<InstanceSource>, Matrix> filter;
    private String className;
    private List<String> features;

    public AnnotateMatrixFilter(Filter<Collection<InstanceSource>, Matrix> filter, String className, List<String> features) {
        super(InstanceSource.class, Matrix.class);
        this.filter = filter;
        this.className = className;
        this.features = features;
    }

    @Override
    protected Matrix doFilter(Collection<InstanceSource> params) {
        Matrix matrix = filter.filter(params);
        for(InstanceSource instanceSource: params) {
            matrix.getRow(instanceSource.getTitle()).getAnnotations().add(getClassId(instanceSource));
        }
        return matrix;
    }

    private String getClassId(InstanceSource instanceSource) {
        Set<String> set = instanceSource.getFeatures();
        for(int i=0; i<features.size(); i++) {
            if(set.contains(features.get(i))) return features.get(i);
        }
        return "other";
    }
}
