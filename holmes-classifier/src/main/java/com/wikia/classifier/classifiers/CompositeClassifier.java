package com.wikia.classifier.classifiers;

import com.wikia.api.model.PageInfo;
import com.wikia.classifier.classifiers.exceptions.ClassifyException;
import com.wikia.classifier.classifiers.model.ClassRelevance;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

public class CompositeClassifier implements Classifier, Serializable {
    private static final long serialVersionUID = -1387740774182989079L;
    private static Logger logger = LoggerFactory.getLogger(CompositeClassifier.class);
    private final List<ClassifierEntry> classifiers;

    public CompositeClassifier(List<ClassifierEntry> classifiers) {
        this.classifiers = classifiers;
    }

    @Override
    public ClassificationResult classify(PageInfo page) throws ClassifyException {
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
