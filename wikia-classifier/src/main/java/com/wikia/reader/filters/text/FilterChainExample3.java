package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 08.04.13
 * Time: 00:38
 */

import com.beust.jcommander.internal.Lists;
import com.wikia.reader.filters.CollectionFilter;
import com.wikia.reader.filters.Filter;
import com.wikia.reader.filters.FilterChain;
import com.wikia.reader.text.data.InstanceSource;
import com.wikia.reader.text.data.PredefinedCodSet;
import com.wikia.reader.text.matrix.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

public class FilterChainExample3 {
    private static Logger logger = LoggerFactory.getLogger(FilterChainExample3.class);

    public static Filter<Collection<InstanceSource>, Matrix> extractPredicateFeatures() {
        FilterChain chain = new FilterChain();
        chain.getChain().add(new CollectionFilter<>(new InstanceSourceDownloaderFilter()));
        chain.getChain().add(new CollectionFilter<>(new TextChunkToWikiStructureFilter()));
        chain.getChain().add(new CompositeExtractorFilter(
                new ExtractCategoriesFilter(),
                new ExtractTemplatePropertiesFilter(),
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
        return new ExtendMatrixFilter(
                extractPredicateFeatures(),
                "__CLASS__",
                Lists.newArrayList("character")
        );
    }

    public static Filter<Collection<InstanceSource>, Instances> instancesBuilder() {
        FilterChain chain = new FilterChain();
        chain.getChain().add(extractPredicateFeaturesWithClass());
        chain.getChain().add(new MatrixToWekaInstancesFilter("__CLASS__"));
        return chain;
    }

    public static void main(String[] args) throws IOException {
        Filter<Collection<InstanceSource>, Instances> collectionInstancesFilter = instancesBuilder();
        Instances instances = collectionInstancesFilter.filter(PredefinedCodSet.getSet());


        ArffSaver saver = new ArffSaver();
        String userHome = System.getProperty( "user.home" );
        saver.setFile(new File(String.valueOf(Paths.get(userHome, "cod3_character.arff"))));
        saver.setInstances(instances);
        saver.writeBatch();
    }
}
