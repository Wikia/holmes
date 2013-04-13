package com.wikia.reader.text.matrix;
/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 13:56
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public interface Vector {
    String getName();

    Collection<String> getAnnotations();

    Map<String, Double> getNonZeroValues();

    double sumNonZeroValues();

    double get(String name);

    void put(String name, double value);
}
