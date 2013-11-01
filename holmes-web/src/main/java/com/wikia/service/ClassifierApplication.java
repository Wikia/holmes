package com.wikia.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class ClassifierApplication extends Application {
    private static Logger logger = LoggerFactory.getLogger(ClassifierApplication.class);
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(ClassificationResource.class);
        return s;
    }
}
