package com.wikia.reader.filters;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 15:17
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FilterBase<TIn, TOut> implements Filter<TIn, TOut> {
    private final Class<TIn> inType;
    private final Class<TOut> outType;

    protected FilterBase(Class<TIn> inType, Class<TOut> outType) {
        this.inType = inType;
        this.outType = outType;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public TOut filter(TIn params) {
        if( !inType.isInstance(params) ) {
            throw new IllegalArgumentException("Argument has wrong type. Was "
                    + params.getClass() + " expected " + inType);
        }
        TOut ret = doFilter(params);
        if( !outType.isInstance(ret) ) {
            throw new IllegalArgumentException("Returnet value has wrong type. Was"
                + ret.getClass() + " expected" + outType);
        }
        return ret;
    }

    protected abstract TOut doFilter(TIn params);
}
