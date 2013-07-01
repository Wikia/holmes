package com.wikia.api.model.builder;

import com.wikia.api.model.LazyPage;
import com.wikia.api.model.PageInfo;

import java.util.List;

/**
 * Author: Artur Dwornik
 * Date: 30.06.13
 * Time: 00:06
 */
public interface LazyPageBuilderContext {
    LazyPageBuilderContext setId(long id);
    LazyPageBuilderContext setTitle(String title);
    LazyPageBuilderContext setNamespace(long ns);
    LazyPageBuilderContext setWikiText(String wikiText);
    LazyPageBuilderContext setLinks(List<PageInfo> links);

    LazyPage build();
}
