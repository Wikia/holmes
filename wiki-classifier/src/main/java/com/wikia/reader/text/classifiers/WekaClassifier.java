package com.wikia.reader.text.classifiers;
/**
 * Author: Artur Dwornik
 * Date: 18.06.13
 * Time: 03:50
 */

import com.beust.jcommander.internal.Lists;
import com.wikia.api.model.PageInfo;
import com.wikia.reader.filters.Filter;
import com.wikia.reader.text.classifiers.exceptions.ClassifyException;
import com.wikia.reader.text.service.model.Classification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Instances;

import java.util.Collection;
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
    public Classification classify(PageInfo source) throws ClassifyException {
        try {
            double[] values = classifier.distributionForInstance(filter.filter(Lists.newArrayList(source)).instance(0));
            String clazz = getClass(values);
            return new Classification(Lists.newArrayList(clazz), values);
        } catch (Exception e) {
            throw new ClassifyException(e);
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
}
