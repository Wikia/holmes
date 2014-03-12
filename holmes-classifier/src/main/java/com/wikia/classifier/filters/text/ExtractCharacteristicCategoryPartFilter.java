package com.wikia.classifier.filters.text;

import com.google.common.collect.Sets;
import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.wikitext.WikiPageCategory;
import com.wikia.classifier.wikitext.WikiPageFeatures;
import com.wikia.classifier.util.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

public class ExtractCharacteristicCategoryPartFilter extends CollectionFilterBase<WikiPageFeatures, SparseMatrix> {
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
                , "enemy"
                , "weapons"
                , "actors"
                , "directors"
                , "celebrities"
                , "performers"
                , "writers"
                , "singers"
                , "musicians"
                , "crew"
                , "cast"
                , "movies"
                , "items"
                , "armors"
                , "films"
                , "books"
                , "authors"
                , "villains"
                , "villain"
                , "rifles"));
    }

    public ExtractCharacteristicCategoryPartFilter(Set<String> characteristicParts) {
        super(WikiPageFeatures.class, SparseMatrix.class);
        this.characteristicParts = characteristicParts;
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageFeatures> params) {
        SparseMatrix matrix = new SparseMatrix();
        int i = 0;
        for(WikiPageFeatures wikiPageFeatures : params) {
            for(String part: characteristicParts) {
                int count = 0;
                for(WikiPageCategory category: wikiPageFeatures.getCategories()) {
                    String name = category.getTitle();
                    name = name.trim().toLowerCase();
                    if (name.contains(part)) count++;
                }
                matrix.put(String.valueOf(i), "special:"+part, count);
            }
            i++;
        }
        return matrix;
    }
}
