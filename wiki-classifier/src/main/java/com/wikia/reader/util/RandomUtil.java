package com.wikia.reader.util;

import java.util.*;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 31.03.13
 * Time: 16:12
 */
public class RandomUtil {
    private static Logger logger = Logger.getLogger(RandomUtil.class.toString());

    public static <T> List<T> randomShuffle(List<T> collection, Random random) {
        ArrayList<T> arrayList = new ArrayList<>(collection);
        for(int i=0; i<collection.size()-1; i++) {
            int index = random.nextInt(collection.size()-i) + i;
            T tmp = collection.get(index);
            collection.set(index, collection.get(i));
            collection.set(i, tmp);
        }
        return arrayList;
    }


    public static <T> List<T> randomTake(List<T> collection, int count) {
        return randomTake(collection, count, new Random());
    }

    public static <T> List<T> randomTake(List<T> collection, int count, Random random) {
        List<T> shuffled = randomShuffle(collection, random);
        return new ArrayList<>(shuffled.subList(0, Math.min(shuffled.size(), count)));
    }
}
