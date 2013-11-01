package com.wikia.classifier.util;

import java.util.Arrays;

public class ArrayUtils {

    public static void sortBoth(int[] a, double [] b) {
        Pair<Integer, Double> [] pairs = new Pair[a.length];
        for (int i=0; i<a.length; i++) {
            pairs[i] = new Pair<>(a[i], b[i]);
        }
        Arrays.sort(pairs);
        for (int  i=0; i<a.length; i++) {
            a[i] = pairs[i].getA();
            b[i] = pairs[i].getB();
        }
    }

    private static class Pair<A extends Comparable<A>,B> implements Comparable<Pair<A,B>> {
        A a;
        B b;

        private Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public A getA() {
            return a;
        }

        public B getB() {
            return b;
        }

        @Override
        public int compareTo(Pair<A, B> o) {
            return a.compareTo(o.a);
        }
    }
}
