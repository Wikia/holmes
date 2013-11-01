package com.wikia.classifier.filters;
/**
 * Author: Artur Dwornik
 * Date: 19.06.13
 * Time: 11:36
 */

import org.testng.Assert;
import org.testng.annotations.Test;

public class FilterBaseTest {

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals("FooFilter", new FooFilter().getName());
    }

    @Test
    public void testFilter() throws Exception {
        FooFilter filter = new FooFilter();
        String result = filter.filter(12L);
        Assert.assertEquals("works", result);
    }

    @Test
    public void allowReturningNull() throws Exception {
        NullFilter filter = new NullFilter();
        String result = filter.filter(12L);
        Assert.assertNull(result);
    }

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void testFilterArgumentTypeVerification() throws Exception {
        Filter filter = new FooFilter();
        filter.filter("asd");
    }

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void testFilterArgumentNullVerification() throws Exception {
        Filter filter = new FooFilter();
        filter.filter(null);
    }

    @Test
    public void testFilterArgumentNullVerificationCanBeDisabled() throws Exception {
        FilterBase filter = new FooFilter();
        filter.setAcceptingNullArguments(true);
        filter.filter(null);
    }

    @Test
    public void testIsAcceptingNullArguments() throws Exception {
        Assert.assertFalse(new FooFilter().isAcceptingNullArguments(), "Default value should be false.");
    }

    @Test
    public void testSetAcceptingNullArguments() throws Exception {
        FilterBase filterBase = new FooFilter();
        filterBase.setAcceptingNullArguments(true);
        Assert.assertTrue(filterBase.isAcceptingNullArguments());
    }

    static class FooFilter extends FilterBase<Long, String> {
        private static final long serialVersionUID = 6902947586100640520L;

        protected FooFilter() {
            super(Long.class, String.class);
        }

        @Override
        protected String doFilter(Long params) {
            return "works";
        }
    }

    static class NullFilter extends FilterBase<Long, String> {
        private static final long serialVersionUID = -777390352756782895L;

        protected NullFilter() {
            super(Long.class, String.class);
        }

        @Override
        protected String doFilter(Long params) {
            return null;
        }
    }
}
