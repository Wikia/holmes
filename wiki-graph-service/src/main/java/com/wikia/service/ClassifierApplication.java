package com.wikia.service;/**
 * Author: Artur Dwornik
 * Date: 13.04.13
 * Time: 13:30
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ClassifierApplication extends Application {
    private static Logger logger = LoggerFactory.getLogger(ClassifierApplication.class);
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(ClassificationResource.class);
        return s;
    }
}
