package com.wikia.reader.text.classifiers;/**
 * Author: Artur Dwornik
 * Date: 14.04.13
 * Time: 16:05
 */

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.wikia.reader.input.TextChunk;
import com.wikia.reader.providers.WikiaHelper;
import com.wikia.reader.text.data.InstanceSource;
import com.wikia.reader.text.data.PredefinedGeneralSet;
import com.wikia.reader.text.service.model.Classification;
import com.wikia.reader.text.service.model.ClassificationCollection;
import com.wikia.reader.util.ThreadPools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class ClassifierManager {
    private static Logger logger = LoggerFactory.getLogger(ClassifierManager.class);
    //private Map<String, Classifier> map = new HashMap<>();
    private final List<ClassifierEntry> classifiers = new ArrayList<>();

    public ClassifierManager() {
        train();
    }

    private void train() {
        final List<InstanceSource> set = getInstanceSources();
        final List<String> classes = Lists.newArrayList(
                  "character"
                , "weapon"
                , "achievement"
                , "item"
                , "location"
                , "level"
                //, "tv_season"
                , "tv_episode"
                , "game"
                , "person"
                , "unit"
                //, "dlc"
                , "organization"
                , "book"
                , "movie");
        //ThreadPools.get().submit(new Runnable() {
        //    @Override
        //    public void run() {
        classifiers.add(new ClassifierEntry("RandomForest(ngram)", new NGramClassifier(set, classes), 1));
        classifiers.add(new ClassifierEntry("RandomForest(puretext)", new PureTextRandomForestClassifier(set, classes), 1));
        classifiers.add(new ClassifierEntry("IBk", new IBkKnnClassifier(set, classes), 0.3));
        classifiers.add(new ClassifierEntry("J48", new J48Classifier(set, classes), 1));
        classifiers.add(new ClassifierEntry("KStar", new KStarClassifier(set, classes), 0.3));
        classifiers.add(new ClassifierEntry("RandomForest", new RandomForestClassifier(set, classes), 1));
        classifiers.add(new ClassifierEntry("NaiveBayes", new NaiveBayesClassifier(set, classes), 1));
        classifiers.add(new ClassifierEntry("BayesNet", new BayesNetClassifier(set, classes), 0.3));
        //    }
        //});

    }

    private List<InstanceSource> getInstanceSources() {
        List<InstanceSource> instanceSources = PredefinedGeneralSet.getSet();
        // instanceSources = filterInstanceSources(instanceSources);
        return instanceSources;
    }

    private List<InstanceSource> filterInstanceSources(List<InstanceSource> instanceSources) {
        List<InstanceSource> filteredSources = new ArrayList<>();
        for( InstanceSource instanceSource: instanceSources ) {
            try {
                TextChunk textChunk = WikiaHelper.fetch(instanceSource.getWikiRoot().toString(), instanceSource.getTitle());
                if( textChunk.getWikiText().length() > 1000 ) {
                    filteredSources.add(instanceSource);
                }
            } catch (IOException e) {
                logger.warn("error getting " + instanceSource);
            }
        }
        return filteredSources;
    }

    public ClassificationCollection classify(InstanceSource source) {
        Map<String,Classification> result = new HashMap<>();
        for(ClassifierEntry entry: classifiers) {
            Classification classification = entry.getClassifier().classify(source);
            classification.setWeight(entry.getWeight());
            result.put(entry.getName(), classification);
        }
        return new ClassificationCollection(result, getAggregatedResult(result.values()));
    }

    private String getAggregatedResult(Collection<Classification> results) {
        Map<String, Double> sums = new HashMap<>();
        double weightSum = 0;
        for(Classification classification: results) {
            String className = classification.getClasses().get(0);
            weightSum += classification.getWeight();
            if( !sums.containsKey(className) ) {
                sums.put(className, 0.0);
            }
            sums.put( className, sums.get(className) + classification.getWeight() );
        }
        String result = "??";
        for(Map.Entry<String, Double> stringEntry: sums.entrySet()) {
            if( stringEntry.getValue() > weightSum/2 ) {
                result = stringEntry.getKey();
            }
        }
        if(result == "other") return "??";
        return result;
    }

    class ClassifierEntry implements Serializable {
        private String name;
        private Classifier classifier;
        private double weight;

        ClassifierEntry(String name, Classifier classifier, double weight) {
            this.name = name;
            this.classifier = classifier;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Classifier getClassifier() {
            return classifier;
        }

        public void setClassifier(Classifier classifier) {
            this.classifier = classifier;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }
}
