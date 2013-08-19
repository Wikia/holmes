package com.wikia.classifier.text.classifiers;
/**
 * Author: Artur Dwornik
 * Date: 18.06.13
 * Time: 02:56
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.wikia.api.model.PageInfo;
import com.wikia.classifier.filters.CollectionFilter;
import com.wikia.classifier.filters.Filter;
import com.wikia.classifier.filters.FilterChain;
import com.wikia.classifier.filters.text.*;
import com.wikia.classifier.input.structured.WikiPageStructure;
import com.wikia.classifier.matrix.Matrix;
import com.wikia.classifier.matrix.SparseMatrix;
import com.wikia.classifier.weka.ArffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unchecked")
public class ClassifierBuilder {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ClassifierBuilder.class);

    private boolean extractCategoriesFilter = true;
    private boolean extractTemplatePropertiesFilter = true;
    private boolean extractSectionsFilter = true;
    private boolean extractCharacteristicCategoryPartFilter = true;
    private boolean extractPlainTextWordsFilter = false;
    private boolean extractSummary1Grams = false;
    private boolean extractSummary2Grams = false;
    private double sparseTermsThreshold = 0.005;
    private final weka.classifiers.Classifier classifier;

    public ClassifierBuilder(weka.classifiers.Classifier classifier) {
        this.classifier = classifier;
    }

    public Classifier train(List<PageInfo> pages, Multimap<String, String> pageFeatureMap, List<String> classes) throws Exception {
        Filter<Collection<PageInfo>, Matrix> f = combine(
                new CollectionFilter(new PageToWikiStructureFilter()),
                buildExtractor( true )
        );
        Matrix matrix = f.filter(pages);
        matrix = new AddClassToMatrixFilter(classes, pageFeatureMap).filter(matrix);
        Filter<Matrix, Instances> matrixInstancesFilter = matrixToInstancesFilter(matrix, classes);
        Instances instances = matrixInstancesFilter.filter(matrix);
        // save instances
        ArffUtil.save(instances, classifier.getClass().getName() + Calendar.getInstance().getTime().toString() + ".arff");

        classifier.buildClassifier(instances);
        Filter<Collection<PageInfo>, Instances> f2 = combine(
                new CollectionFilter(new PageToWikiStructureFilter()),
                buildExtractor( true ),
                matrixInstancesFilter
        );
        return new WekaClassifier(classifier, f2, classes);
    }

    protected <TIn, TOut> Filter<TIn, TOut> combine( Filter...filters ) {
        FilterChain filterChain = new FilterChain();
        for( Filter filter: filters ) {
            filterChain.getChain().add(filter);
        }
        return filterChain;
    }

    protected Filter<Collection<WikiPageStructure>, SparseMatrix> buildExtractor( boolean train ) {
        List<Filter<Collection<WikiPageStructure>, SparseMatrix>> extractors = new ArrayList<>();
        if( extractCategoriesFilter ) {
            extractors.add(new ExtractCategoriesFilter());
        }
        if( extractTemplatePropertiesFilter ) {
            extractors.add(new ExtractTemplatePropertiesFilter());
        }
        if( extractSectionsFilter ) {
            extractors.add(new ExtractSectionsFilter());
        }
        if( extractCharacteristicCategoryPartFilter ) {
            extractors.add(new ExtractCharacteristicCategoryPartFilter());
        }
        if( extractPlainTextWordsFilter ) {
            extractors.add(new ExtractPlainTextWordsFilter());
        }
        if(extractSummary1Grams) {
            extractors.add(new ExtractSummaryFilter(1));
        }
        if(extractSummary2Grams) {
            extractors.add(new ExtractSummaryFilter(2));
        }
        Filter filter = new CompositeExtractorFilter(extractors);
        if( train && sparseTermsThreshold > 0) {
            filter = filterSparseTerms( filter, sparseTermsThreshold );
        }
        return filter;
    }

    protected Filter filterSparseTerms( Filter filter, double filterSparseTerms ) {
        FilterChain chain = new FilterChain();
        chain.getChain().add(filter);
        chain.getChain().add(new RemoveSparseTermsFilter(filterSparseTerms));
        return chain;
    }


    protected Filter<Matrix, Instances> matrixToInstancesFilter(Matrix matrix, List<String> classes) {
        List<String> cols = Lists.newArrayList(matrix.getColumnNames());
        FilterChain chain = new FilterChain();
        List<String> cls = new ArrayList<>(classes);
        cls.add("other");
        chain.getChain().add(new MatrixToWekaInstancesFilter("__CLASS__", cls, cols));

        return chain;
    }

    public ClassifierBuilder setExtractCategoriesFilter(boolean extractCategoriesFilter) {
        this.extractCategoriesFilter = extractCategoriesFilter;
        return this;
    }

    public ClassifierBuilder setExtractTemplatePropertiesFilter(boolean extractTemplatePropertiesFilter) {
        this.extractTemplatePropertiesFilter = extractTemplatePropertiesFilter;
        return this;
    }

    public ClassifierBuilder setExtractSectionsFilter(boolean extractSectionsFilter) {
        this.extractSectionsFilter = extractSectionsFilter;
        return this;
    }

    public ClassifierBuilder setExtractCharacteristicCategoryPartFilter(boolean extractCharacteristicCategoryPartFilter) {
        this.extractCharacteristicCategoryPartFilter = extractCharacteristicCategoryPartFilter;
        return this;
    }

    public ClassifierBuilder setExtractPlainTextWordsFilter(boolean extractPlainTextWordsFilter) {
        this.extractPlainTextWordsFilter = extractPlainTextWordsFilter;
        return this;
    }

    public ClassifierBuilder setSparseTermsThreshold(double sparseTermsThreshold) {
        this.sparseTermsThreshold = sparseTermsThreshold;
        return this;
    }

    public ClassifierBuilder setExtractSummary1Grams(boolean extractSummary1Grams) {
        this.extractSummary1Grams = extractSummary1Grams;
        return this;
    }

    public ClassifierBuilder setExtractSummary2Grams(boolean extractSummary2Grams) {
        this.extractSummary2Grams = extractSummary2Grams;
        return this;
    }
}
