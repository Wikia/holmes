package com.wikia.api.model.builder;

/**
 * Author: Artur Dwornik
 * Date: 30.06.13
 * Time: 00:56
 */
public interface LazyPageBuilder {
    LazyPageBuilderContext byId(long pageId);
    LazyPageBuilderContext byTitle(String title);
}
