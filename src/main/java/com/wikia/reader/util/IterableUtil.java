package com.wikia.reader.util;/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 17:44
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IterableUtil {
    private static Logger logger = LoggerFactory.getLogger(IterableUtil.class);

    public static <T> Map<T, Integer> countOccurrences(Iterable<T> collection) {
        Map<T, Integer> counts = new HashMap<>();
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            T nxt = iterator.next();
            if( counts.containsKey(nxt) ){
                Integer c = counts.get(nxt);
                counts.put(nxt, c+1);
            } else {
                counts.put(nxt, 1);
            }
        }
        return counts;
    }
}
