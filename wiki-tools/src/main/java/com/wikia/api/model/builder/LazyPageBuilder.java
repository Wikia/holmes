package com.wikia.api.model.builder;


public interface LazyPageBuilder {
    LazyPageBuilderContext byId(long pageId);
    LazyPageBuilderContext byTitle(String title);
}
