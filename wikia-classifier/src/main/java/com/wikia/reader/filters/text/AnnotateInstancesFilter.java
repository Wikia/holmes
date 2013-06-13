package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 09.04.13
 * Time: 16:41
 */

import com.wikia.reader.filters.CollectionFilterBase;
import com.wikia.reader.text.data.InstanceSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Instances;

import java.util.Collection;
import java.util.List;

public class AnnotateInstancesFilter extends CollectionFilterBase<InstanceSource, Instances> {
    private static Logger logger = LoggerFactory.getLogger(AnnotateInstancesFilter.class);
    private final List<String> annotations;

    protected AnnotateInstancesFilter(List<String> annotations) {
        super(InstanceSource.class, Instances.class);
        this.annotations = annotations;
    }

    @Override
    protected Instances doFilter(Collection<InstanceSource> params) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
