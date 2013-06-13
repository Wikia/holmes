package com.wikia.reader.text.service.model;/**
 * Author: Artur Dwornik
 * Date: 14.04.13
 * Time: 10:16
 */

import com.beust.jcommander.internal.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name = "classifications")
public class ClassificationCollection {
    private static Logger logger = LoggerFactory.getLogger(ClassificationCollection.class);
    private Map<String,Classification> classifications;
    private String aggregatedResult;

    public ClassificationCollection() {
        this(new HashMap(), "");
    }

    public ClassificationCollection(Map<String,Classification> classifications, String aggregatedResult) {
        this.classifications = classifications;
        this.aggregatedResult = aggregatedResult;
    }

    public Map<String,Classification> getClassifications() {
        return classifications;
    }

    public void setClassifications(Map<String,Classification> classifications) {
        this.classifications = classifications;
    }

    public String getAggregatedResult() {
        return aggregatedResult;
    }

    public void setAggregatedResult(String aggregatedResult) {
        this.aggregatedResult = aggregatedResult;
    }
}
