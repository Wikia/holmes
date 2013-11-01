package com.wikia.api.model.builder;

import com.wikia.api.model.LazyPage;
import com.wikia.api.model.PageInfo;

import java.util.List;


public interface LazyPageBuilderContext {
    LazyPageBuilderContext setId(long id);
    LazyPageBuilderContext setTitle(String title);
    LazyPageBuilderContext setNamespace(long ns);
    LazyPageBuilderContext setWikiText(String wikiText);
    LazyPageBuilderContext setLinks(List<PageInfo> links);

    LazyPage build();
}
