package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 12.04.13
 * Time: 10:05
 */

import com.beust.jcommander.internal.Lists;
import com.wikia.reader.filters.CollectionFilter;
import com.wikia.reader.filters.Filter;
import com.wikia.reader.filters.FilterChain;
import com.wikia.reader.text.data.InstanceSource;
import com.wikia.reader.text.data.PredefinedGeneralSet;
import com.wikia.reader.text.matrix.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

public class FilterChainExample5 {
    private static Logger logger = LoggerFactory.getLogger(FilterChainExample5.class);

    public static Filter<Collection<InstanceSource>, Matrix> extractPredicateFeatures() {
        FilterChain chain = new FilterChain();
        chain.getChain().add(new CollectionFilter<>(new InstanceSourceDownloaderFilter()));
        chain.getChain().add(new CollectionFilter<>(new TextChunkToWikiStructureFilter()));
        chain.getChain().add(new CompositeExtractorFilter(
                new ExtractCategoriesFilter(),
                new ExtractTemplatePropertiesFilter(),
                new ExtractSectionsFilter(),
                new CharacteristicCategoryPartExtractorFilter(),
                extractPlainText()));
        return chain;
    }

    public static Filter extractPlainText() {
        FilterChain chain = new FilterChain();
        chain.getChain().add(new ExtractPlainTextWordsFilter());
        chain.getChain().add(new RemoveSparseTermsFilter(0.5));
        return chain;
    }

    public static Filter<Collection<InstanceSource>, Matrix> extractPredicateFeaturesWithClass() {
        return new AnnotateMatrixFilter(
                extractPredicateFeatures(),
                "__CLASS__",
                Lists.newArrayList("character", "weapon", "achievement", "item", "location", "tv_season", "tv_episode")
        );
    }

    public static Filter<Collection<InstanceSource>, Instances> instancesBuilder() {
        FilterChain chain = new FilterChain();
        chain.getChain().add(extractPredicateFeaturesWithClass());
        chain.getChain().add(new AnnotatedMatrixToWekaInstancesFilter("__CLASS__",
                Lists.newArrayList("character", "weapon", "achievement", "item", "location", "tv_season", "tv_episode", "other")));
        return chain;
    }

    public static void main(String[] args) throws IOException {
        Filter<Collection<InstanceSource>, Instances> collectionInstancesFilter = instancesBuilder();
        Instances instances = collectionInstancesFilter.filter(PredefinedGeneralSet.getSet());


        ArffSaver saver = new ArffSaver();
        String userHome = System.getProperty( "user.home" );
        saver.setFile(new File(String.valueOf(Paths.get(userHome, "cod5_character.arff"))));
        saver.setInstances(instances);
        saver.writeBatch();
    }
}
