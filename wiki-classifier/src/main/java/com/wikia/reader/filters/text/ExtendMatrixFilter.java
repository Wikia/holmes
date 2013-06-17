package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 21:33
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

public class ExtendMatrixFilter extends CollectionFilterBase<InstanceSource, Matrix> {
    private static Logger logger = LoggerFactory.getLogger(ExtendMatrixFilter.class);
    private Filter<Collection<InstanceSource>, Matrix> filter;
    private String className;
    private List<String> features;

    protected ExtendMatrixFilter(Filter<Collection<InstanceSource>, Matrix> filter, String className, List<String> features) {
        super(InstanceSource.class, Matrix.class);
        this.filter = filter;
        this.className = className;
        this.features = features;
    }

    @Override
    protected Matrix doFilter(Collection<InstanceSource> params) {
        Matrix matrix = filter.filter(params);
        for(InstanceSource instanceSource: params) {
            matrix.put(instanceSource.getTitle(), className, getClassId(instanceSource));
        }
        return matrix;
    }

    private double getClassId(InstanceSource instanceSource) {
        Set<String> set = instanceSource.getFeatures();
        for(int i=0; i<features.size(); i++) {
            if(set.contains(features.get(i))) return i+1;
        }
        return features.size()+1;
    }
}
