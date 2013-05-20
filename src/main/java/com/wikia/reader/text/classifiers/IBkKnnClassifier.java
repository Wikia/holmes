package com.wikia.reader.text.classifiers;/**
 * Author: Artur Dwornik
 * Date: 14.04.13
 * Time: 11:18
 */

import com.beust.jcommander.internal.Lists;
import com.wikia.reader.filters.CollectionFilter;
import com.wikia.reader.filters.Filter;
import com.wikia.reader.filters.FilterChain;
import com.wikia.reader.filters.text.*;
import com.wikia.reader.text.data.InstanceSource;
import com.wikia.reader.text.matrix.Matrix;
import com.wikia.reader.text.service.model.Classification;
import com.wikia.reader.weka.ArffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class IBkKnnClassifier implements Classifier {
    private static Logger logger = LoggerFactory.getLogger(IBkKnnClassifier.class);
    private final List<InstanceSource> sources;
    private Filter<Collection<InstanceSource>, Instances> instancesBuilder;
    private IBk ibk;
    private List<String> classes;

    public IBkKnnClassifier(List<InstanceSource> sources) {
        this(sources, Lists.newArrayList("character", "weapon", "achievement", "item", "location", "tv_season", "tv_episode"));
    }

    public IBkKnnClassifier(List<InstanceSource> sources, List<String> classes) {
        this.sources = sources;
        this.classes = classes;
        train(sources);
    }

    @Override
    public Classification classify(InstanceSource source) {
        Instance instance = instancesBuilder.filter(Lists.newArrayList(source)).get(0);
        try {
            double[] doubles = ibk.distributionForInstance(instance);
            String clazz = getClass(doubles);
            return new Classification(Lists.newArrayList(clazz), doubles);
        } catch (Exception e) {
            logger.error("cannot classify instance.", e);
            return new Classification();
        }
    }

    private String getClass(double[] doubles) {
        double best = 0;
        int bestind = 0;
        for(int i=0; i<doubles.length; i++) {
            if(doubles[i] > best) {
                bestind = i;
                best = doubles[i];
            }
        }
        if(best < 0.01 || classes.size() <= bestind) return "other";
        return classes.get(bestind);
    }

    protected void train(List<InstanceSource> sources) {
        Matrix matrix = extractPredicateFeaturesForFilter().filter(sources);
        instancesBuilder = instancesBuilder(Lists.newArrayList(matrix.getColumnNames()));
        ibk = new IBk(1);
        try {
            Instances instances = instancesBuilder.filter(sources);
            ibk.buildClassifier(instances);
            ArffUtil.save(instances, "IBk_dump.arff");
        } catch (Exception e) {
            logger.error("Unexpected exception during training.",e);
        }
    }

    public Filter<Collection<InstanceSource>, Matrix> extractPredicateFeatures() {
        FilterChain chain = new FilterChain();
        chain.getChain().add(new CollectionFilter<>(new InstanceSourceDownloaderFilter()));
        chain.getChain().add(new CollectionFilter<>(new TextChunkToWikiStructureFilter()));
        chain.getChain().add(new CompositeExtractorFilter(
                new ExtractCategoriesFilter(),
                new ExtractTemplatePropertiesFilter(),
                new ExtractSectionsFilter(),
                new CharacteristicCategoryPartExtractorFilter(),
                new ExtractPlainTextWordsFilter()));
        //chain.getChain().add(new MatrixColumnFilter(features));
        return chain;
    }

    public Filter<Collection<InstanceSource>, Matrix> extractPredicateFeaturesForFilter() {
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

    public Filter extractPlainText() {
        FilterChain chain = new FilterChain();
        chain.getChain().add(new ExtractPlainTextWordsFilter());
        chain.getChain().add(new RemoveSparseTermsFilter(0.5));
        return chain;
    }

    public Filter<Collection<InstanceSource>, Matrix> extractPredicateFeaturesWithClass() {
        return new AnnotateMatrixFilter(
                extractPredicateFeatures(),
                "__CLASS__",
                classes
        );
    }

    public Filter<Collection<InstanceSource>, Instances> instancesBuilder(List<String> cols) {
        FilterChain chain = new FilterChain();
        chain.getChain().add(extractPredicateFeaturesWithClass());
        List<String> cls = new ArrayList<>(classes);
        cls.add("other");
        chain.getChain().add(new AnnotatedMatrixToWekaInstancesFilter("__CLASS__", cls, cols));
        return chain;
    }
}
