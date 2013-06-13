package com.wikia.reader.filters;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 19:43
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionFilter<TInElement, TOutElement> extends FilterBase<Collection<TInElement>, Collection<TOutElement>> {
    private static Logger logger = LoggerFactory.getLogger(CollectionFilter.class);
    private final Filter<TInElement, TOutElement> innerFilter;

    public CollectionFilter( Filter<TInElement, TOutElement> innerFilter) {
        super((Class<Collection<TInElement>>) (Class) Collection.class, (Class<Collection<TOutElement>>) (Class) Collection.class);
        this.innerFilter = innerFilter;
    }

    @Override
    protected Collection<TOutElement> doFilter(Collection<TInElement> params) {
        List<TOutElement> outElementList = new ArrayList<>();
        for(TInElement param: params) {
            TOutElement out = innerFilter.filter(param);
            outElementList.add(out);
        }
        return outElementList;
    }
}
