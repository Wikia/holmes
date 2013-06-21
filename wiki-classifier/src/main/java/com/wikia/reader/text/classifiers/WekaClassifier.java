package com.wikia.reader.text.classifiers;
/**
 * Author: Artur Dwornik
 * Date: 18.06.13
 * Time: 03:50
 */

import com.google.common.collect.Lists;
import com.wikia.api.model.PageInfo;
import com.wikia.reader.filters.Filter;
import com.wikia.reader.text.classifiers.exceptions.ClassifyException;
import com.wikia.reader.text.classifiers.model.ClassRelevance;
import com.wikia.reader.text.classifiers.model.ClassificationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WekaClassifier implements Classifier {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(WekaClassifier.class);
    private final weka.classifiers.Classifier classifier;
    private final Filter<Collection<PageInfo>, Instances> filter;
    private final List<String> classes;

    public WekaClassifier(weka.classifiers.Classifier classifier, Filter<Collection<PageInfo>, Instances> filter, List<String> classes) {
        this.classifier = classifier;
        this.filter = filter;
        this.classes = classes;
    }

    @Override
    public ClassificationResult classify(PageInfo source) throws ClassifyException {
        try {
            double[] values = classifier.distributionForInstance(filter.filter(Lists.newArrayList(source)).instance(0));
            return buildClassificationResult( values );
        } catch (Exception e) {
            throw new ClassifyException("Error while classification of page content.",e);
        }
    }

    private ClassificationResult buildClassificationResult( double[] estimates ) {
        if( classes.size() + 1 != estimates.length ) {
            logger.warn(String.format("classes.size() (%d) != (%d) estimates.length"
                    , classes.size()
                    , estimates.length
            ));
        }
        List<ClassRelevance> classRelevanceList = new ArrayList<>();
        for(int i=0; i < Math.min(estimates.length, classes.size() + 1); i++) {
            String className;
            if( i < classes.size() ) {
                className = classes.get(i);
            } else {
                className = "other";
            }
            classRelevanceList.add(new ClassRelevance(className, estimates[i]));
        }
        Collections.sort(classRelevanceList);
        Collections.reverse(classRelevanceList);
        String selectedClass = "other";
        if( classRelevanceList.size() > 0 && classRelevanceList.get(0).getRelevance() > 0.01 ) {
            selectedClass = classRelevanceList.get(0).toString();
        }
        return new ClassificationResult(selectedClass, classRelevanceList);
    }
}
