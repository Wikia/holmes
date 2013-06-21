package com.wikia.reader.text.classifiers;/**
 * Author: Artur Dwornik
 * Date: 14.04.13
 * Time: 16:05
 */

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.wikia.api.model.Page;
import com.wikia.api.model.PageInfo;
import com.wikia.api.service.PageService;
import com.wikia.api.service.PageServiceFactory;
import com.wikia.reader.text.classifiers.exceptions.ClassifyException;
import com.wikia.reader.text.classifiers.model.ClassRelevance;
import com.wikia.reader.text.classifiers.model.ClassificationResult;
import com.wikia.reader.text.data.InstanceSource;
import com.wikia.reader.text.data.PredefinedGeneralSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;


// TODO: unhardcode me
public class CompositeClassifier {
    private static Logger logger = LoggerFactory.getLogger(CompositeClassifier.class);
    //private Map<String, Classifier> map = new HashMap<>();
    private final List<ClassifierEntry> classifiers = new ArrayList<>();

    public CompositeClassifier() {
        train();
    }

    private void train() {
        List<PageInfo> set = null;
        Multimap<String, String> multimap = HashMultimap.create();
        try {
            set = getInstanceSources(multimap);
        } catch (IOException e) {
            logger.error("Error", e);
        }
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
        //classifiers.add(new ClassifierEntry("RandomForest(ngram)",    new NGramClassifier(set, classes), 1));
        //classifiers.add(new ClassifierEntry("RandomForest(puretext)", new PureTextRandomForestClassifier(set, classes), 1));
        try {
            // TODO: factory for that
            classifiers.add(new ClassifierEntry("IBk",                    new ClassifierBuilder(new IBk()).train(set, multimap, classes), 0.3));
            classifiers.add(new ClassifierEntry("J48",                    new ClassifierBuilder(new J48()).train(set, multimap, classes), 1));
            classifiers.add(new ClassifierEntry("KStar",                  new ClassifierBuilder(new KStar()).train(set, multimap, classes), 0.3));
            RandomForest randomForest = new RandomForest(); randomForest.setNumTrees(200);
            classifiers.add(new ClassifierEntry("RandomForest",           new ClassifierBuilder(randomForest).train(set, multimap, classes), 1));
            classifiers.add(new ClassifierEntry("NaiveBayes",             new ClassifierBuilder(new NaiveBayes()).train(set, multimap, classes), 1));
            classifiers.add(new ClassifierEntry("BayesNet",               new ClassifierBuilder(new BayesNet()).train(set, multimap, classes), 0.3));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //    }
        //});

    }

    private List<PageInfo> getInstanceSources(Multimap<String, String> multimap) throws IOException {
        List<InstanceSource> instanceSources = PredefinedGeneralSet.getSet();
        PageServiceFactory pageServiceFactory = new PageServiceFactory();
        List<PageInfo> pageInfoList = new ArrayList<>();
        for(InstanceSource instanceSource: instanceSources) {
            PageService pageService = pageServiceFactory.get(instanceSource.getWikiRoot());
            Page page = pageService.getPage(instanceSource.getTitle());
            pageInfoList.add(page);
            multimap.putAll(page.getTitle(), instanceSource.getFeatures());
        }
        return pageInfoList;
    }

    public ClassificationResult classify(InstanceSource instanceSource) {
        try {
            PageServiceFactory pageServiceFactory = new PageServiceFactory();
            PageService pageService = pageServiceFactory.get(instanceSource.getWikiRoot());
            Page page;

            page = pageService.getPage(instanceSource.getTitle());
            Map<String, ClassificationResult> result = new HashMap<>();
            double weightSum = 0;
            Map<String, Double> classifications = new HashMap<>();
            for ( ClassifierEntry entry: classifiers ) {
                ClassificationResult classification = entry.getClassifier().classify(page);
                for( ClassRelevance relevance: classification.getClasses() ) {
                    if( !classifications.containsKey( relevance.getClassName() ) ) {
                        classifications.put( relevance.getClassName(), relevance.getRelevance() );
                    } else {
                        classifications.put(relevance.getClassName()
                                , classifications.get(relevance.getClassName()) +  relevance.getRelevance());
                    }
                }
                weightSum += entry.getWeight();
                result.put(entry.getName(), classification);
            }
            for( Map.Entry<String, Double> entry: classifications.entrySet() ) {
                entry.setValue( entry.getValue() / weightSum );
            }

            return buildClassificationResult(classifications);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error while classification.", e);
        } catch (ClassifyException e) {
            throw new RuntimeException("Runtime error while classification.",e);
        }
    }

    private ClassificationResult buildClassificationResult( Map<String, Double> classifications ) {
        // TODO: create strategy from that
        List<ClassRelevance> classRelevanceList = new ArrayList<>();
        for(Map.Entry<String, Double> classification: classifications.entrySet()) {
            classRelevanceList.add(new ClassRelevance(classification.getKey(), classification.getValue()));
        }
        Collections.sort(classRelevanceList);
        Collections.reverse(classRelevanceList);
        String selectedClass = "other";
        if( classRelevanceList.size() > 0 && classRelevanceList.get(0).getRelevance() > 0.2 ) {
            if ( classRelevanceList.size() == 1
                    || classRelevanceList.get(0).getRelevance() / classRelevanceList.get(1).getRelevance() > 1.2)
            selectedClass = classRelevanceList.get(0).getClassName();
        }
        return new ClassificationResult(selectedClass, classRelevanceList);
    }

    static class ClassifierEntry implements Serializable {
        private static final long serialVersionUID = 2590121296240308815L;
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
