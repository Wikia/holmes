package com.wikia.reader.filters;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 20:54
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter {
    private static Logger logger = LoggerFactory.getLogger(FilterChain.class);
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
