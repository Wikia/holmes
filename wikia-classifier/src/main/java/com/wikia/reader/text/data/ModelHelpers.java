package com.wikia.reader.text.data;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 01:46
 */

import com.beust.jcommander.internal.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ModelHelpers {
    private static Logger logger = LoggerFactory.getLogger(ModelHelpers.class);

    public static Instances getCodInstances() throws IOException {
        List<InstanceSource> set = PredefinedCodSet.getSet();
        InstanceSourceProcessor processor = new InstanceSourceProcessor(
                Lists.newArrayList("character"));
        return processor.getInstances(set);
    }

    /*
    public static Classifier getCodJ48Model() throws Exception {

    }
    */

    public static void main(String[] args) throws IOException {
        Instances cod = getCodInstances();
        ArffSaver saver = new ArffSaver();
        saver.setFile(new File("/tmp/cod.arff"));
        saver.setInstances(cod);
        saver.writeBatch();
    }
}
