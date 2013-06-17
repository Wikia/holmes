package com.wikia.reader.text.classifiers;/**
 * Author: Artur Dwornik
 * Date: 28.04.13
 * Time: 03:56
 */

import com.wikia.reader.filters.CollectionFilter;
import com.wikia.reader.filters.Filter;
import com.wikia.reader.filters.FilterChain;
import com.wikia.reader.filters.text.InstanceSourceDownloaderFilter;
import com.wikia.reader.filters.text.TextChunkToWikiStructureFilter;
import com.wikia.reader.text.data.InstanceSource;
import com.wikia.reader.text.matrix.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class PureTextRandomForestClassifier extends RandomForestClassifier {
    private static Logger logger = LoggerFactory.getLogger(PureTextRandomForestClassifier.class);

    public PureTextRandomForestClassifier(List<InstanceSource> sources, List<String> classes) {
        super(sources, classes);
        setNumTrees(150);
    }

    @Override
    public Filter<Collection<InstanceSource>, Matrix> extractPredicateFeaturesForFilter() {
        FilterChain chain = new FilterChain();
        chain.getChain().add(new CollectionFilter<>(new InstanceSourceDownloaderFilter()));
        chain.getChain().add(new CollectionFilter<>(new TextChunkToWikiStructureFilter()));
        chain.getChain().add(extractPlainText(0.05));
        return chain;
    }

    @Override
    protected String getDumpFileName() {
        return "randomForest_pureText_dump.arff";
    }
}
