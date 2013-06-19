package com.wikia.reader.text.classifiers;
/**
 * Author: Artur Dwornik
 * Date: 18.06.13
 * Time: 02:56
 */

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Multimap;
import com.wikia.api.model.PageInfo;
import com.wikia.reader.filters.CollectionFilter;
import com.wikia.reader.filters.Filter;
import com.wikia.reader.filters.FilterChain;
import com.wikia.reader.filters.text.*;
import com.wikia.reader.input.structured.WikiPageStructure;
import com.wikia.reader.text.matrix.Matrix;
import com.wikia.reader.text.matrix.SparseMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Instances;

import java.util.ArrayList;
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
        classifier.buildClassifier(matrixInstancesFilter.filter(matrix));
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
}
