package com.wikia.classifier.text.classifiers;

import com.google.common.collect.*;
import com.wikia.api.model.PageInfo;
import com.wikia.classifier.filters.CollectionFilter;
import com.wikia.classifier.filters.Filter;
import com.wikia.classifier.filters.FilterChain;
import com.wikia.classifier.filters.text.*;
import com.wikia.classifier.input.structured.WikiPageStructure;
import com.wikia.classifier.matrix.Matrix;
import com.wikia.classifier.matrix.SparseMatrix;
import com.wikia.classifier.text.classifiers.model.PageWithType;
import com.wikia.classifier.weka.ArffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Instances;

import java.util.*;

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
    private boolean extractSummary3Grams = false;
    private boolean extractTitle = true;
    private double sparseTermsThreshold = 0.005d;
    private final weka.classifiers.Classifier classifier;

    public ClassifierBuilder(weka.classifiers.Classifier classifier) {
        this.classifier = classifier;
    }

    private List<String> getTypes( List<PageWithType> pages ) {
        Set<String> types = new HashSet<>();
        for ( PageWithType page: pages ) {
            types.add( page.getType() );
        }
        return new ArrayList(types);
    }

    private Multimap<String,String> getTypeMap( List<PageWithType> pages ) {
        Multimap<String,String> typeMap = HashMultimap.create();
        for ( PageWithType page: pages ) {
            typeMap.put(page.getTitle(), page.getType());
        }
        return typeMap;
    }

    public Classifier train(List<PageWithType> pages) throws Exception {
        Filter<Collection<PageInfo>, Matrix> f = combine(
                new CollectionFilter(new PageToWikiStructureFilter()),
                buildExtractor( true )
        );
        List<String> classes = getTypes(pages);
        Multimap<String, String> pageTypeMap = getTypeMap(pages);
        Matrix matrix = f.filter((List<PageInfo>) (List) pages);
        matrix = new AddClassToMatrixFilter(classes, pageTypeMap).filter(matrix);
        Filter<Matrix, Instances> matrixInstancesFilter = matrixToInstancesFilter(matrix, classes);
        Instances instances = matrixInstancesFilter.filter(matrix);
        // save instances
        // TODO: removeMe
        // ArffUtil.save(instances, classifier.getClass().getName() + Calendar.getInstance().getTime().toString() + ".arff");

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
        if(extractSummary3Grams) {
            extractors.add(new ExtractSummaryFilter(3));
        }
        if(extractTitle) {
            extractors.add(new TitleNGramFilter(1));
        }
        Filter filter = new CompositeExtractorFilter(extractors);
        if( train && sparseTermsThreshold > 0) {
            filter = filterSparseTerms( filter, sparseTermsThreshold );
        }
        filter = makeNormalizedFilter(filter);
        return filter;
    }

    protected Filter makeNormalizedFilter( Filter filter ) {
        FilterChain chain = new FilterChain();
        chain.getChain().add(filter);
        chain.getChain().add(new NormalizeMatrixFilter());
        return chain;
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

    public ClassifierBuilder setExtractSummary3Grams(boolean extractSummary2Grams) {
        this.extractSummary2Grams = extractSummary2Grams;
        return this;
    }

    public ClassifierBuilder setExtractTitle( boolean extractTitle ) {
        this.extractTitle = extractTitle;
        return this;
    }
}
