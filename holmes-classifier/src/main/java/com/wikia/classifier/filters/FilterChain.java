package com.wikia.classifier.filters;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter {
    private static final long serialVersionUID = 1047086739964208768L;
    private final List<Filter> chain = new ArrayList<>();

    public FilterChain() {
    }

    @Override
    public String getName() {
        return "FilterChain";
    }

    @Override
    public Object filter(Object params) {
        Object current = params;
        for(Filter filter: chain) {
            current = filter.filter(current);
        }
        return current;
    }

    public List<Filter> getChain() {
        return chain;
    }
}
