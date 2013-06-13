package com.wikia.reader.filters;

import java.io.Serializable;

/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 15:14
 */
public interface Filter<TIn, TOut> extends Serializable {
    String getName();
    TOut filter(TIn params);
}
