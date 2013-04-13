package com.wikia.reader.filters;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 15:42
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public abstract class CollectionFilterBase<TElement, TOut> implements Filter<Collection<TElement>, TOut> {
    private static Logger logger = LoggerFactory.getLogger(CollectionFilterBase.class);
    private Class<TElement> elementClass;
    private Class<TOut> outType;

    protected CollectionFilterBase(Class<TElement> elementClass, Class<TOut> outType) {
        this.elementClass = elementClass;
        this.outType = outType;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public TOut filter(Collection<TElement> params) {
        if( !Collection.class.isInstance(params) ) {
            throw new IllegalArgumentException("Argument has wrong type. Was "
                    + params.getClass() + " expected Collection");
        }
        TOut ret = doFilter(params);
        if( !outType.isInstance(ret) ) {
            throw new IllegalArgumentException("Returnet value has wrong type. Was"
                    + ret.getClass() + " expected" + outType);
        }
        return ret;
    }

    protected abstract TOut doFilter(Collection<TElement> params);
}
