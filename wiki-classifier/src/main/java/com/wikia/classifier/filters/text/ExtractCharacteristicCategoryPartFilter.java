package com.wikia.classifier.filters.text;/**
 * Author: Artur Dwornik
 * Date: 13.04.13
 * Time: 19:41
 */

import com.google.common.collect.Sets;
import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.input.structured.WikiPageCategory;
import com.wikia.classifier.input.structured.WikiPageStructure;
import com.wikia.classifier.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

public class ExtractCharacteristicCategoryPartFilter extends CollectionFilterBase<WikiPageStructure, SparseMatrix> {
    private static final long serialVersionUID = 8466868566417222816L;
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ExtractCharacteristicCategoryPartFilter.class);
    private Set<String> characteristicParts;

    public ExtractCharacteristicCategoryPartFilter() {
        this(Sets.newHashSet("season"
                , "character"
                , "characters"
                , "episodes"
                , "missions"
                , "levels"
                , "people"
                , "episodes"
                , "games"
                , "creatures"
                , "units"
                , "novels"
                , "stories"
                , "playstation"
                , "xbox"
                , "enemies"
                , "weapons"
                , "actors"
                , "directors"
                , "celebrities"
                , "performers"
                , "writers"
                , "crew"
                , "cast"
                , "movies"
                , "items"
                , "armors"
                , "films"
                , "books"
                , "authors"
                , "rifles"));
    }

    public ExtractCharacteristicCategoryPartFilter(Set<String> characteristicParts) {
        super(WikiPageStructure.class, SparseMatrix.class);
        this.characteristicParts = characteristicParts;
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageStructure> params) {
        SparseMatrix matrix = new SparseMatrix();
        for(WikiPageStructure wikiPageStructure: params) {
            for(String part: characteristicParts) {
                int count = 0;
                for(WikiPageCategory category: wikiPageStructure.getCategories()) {
                    String name = category.getTitle();
                    name = name.trim().toLowerCase();
                    if (name.contains(part)) count++;
                }
                matrix.put(wikiPageStructure.getTitle(), "special:"+part, count);
            }
        }
        return matrix;
    }
}
