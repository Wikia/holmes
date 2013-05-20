package com.wikia.reader.weka;/**
 * Author: Artur Dwornik
 * Date: 14.04.13
 * Time: 16:59
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ArffUtil {
    private static Logger logger = LoggerFactory.getLogger(ArffUtil.class);

    public static void save(Instances instances, String name) throws IOException {
        ArffSaver saver = new ArffSaver();
        String userHome = System.getProperty( "user.home" );
        saver.setFile(new File(String.valueOf(Paths.get(userHome, name))));
        saver.setInstances(instances);
        saver.writeBatch();
    }
}
