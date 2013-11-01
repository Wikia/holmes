package com.wikia.classifier.filters;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionFilter<TInElement, TOutElement> extends FilterBase<Collection<TInElement>, Collection<TOutElement>> {
    private static final long serialVersionUID = 5952824510014559036L;
    private final Filter<TInElement, TOutElement> innerFilter;

    public CollectionFilter( Filter<TInElement, TOutElement> innerFilter) {
        super(new  TypeToken <Collection<TInElement>>() {}.getRawType() , new  TypeToken <Collection<TOutElement>>() {}.getRawType());
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
