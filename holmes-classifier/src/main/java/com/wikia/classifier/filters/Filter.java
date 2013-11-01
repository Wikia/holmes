package com.wikia.classifier.filters;

import java.io.Serializable;


public interface Filter<TIn, TOut> extends Serializable {
    String getName();
    TOut filter(TIn params);
}
