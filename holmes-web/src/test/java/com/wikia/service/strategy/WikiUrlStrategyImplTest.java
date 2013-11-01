package com.wikia.service.strategy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WikiUrlStrategyImplTest {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(WikiUrlStrategyImplTest.class);
    WikiUrlStrategy strategy;

    @BeforeMethod
    public void setUp() throws Exception {
        strategy = new WikiUrlStrategyImpl();
    }

    @Test
    public void testGetUrlWikiaDbName() throws Exception {
        assertEquals(strategy.getUrl("foo"), new URL("http://foo.wikia.com/"));
    }

    @Test
    public void testGetUrlDomainNoProtocol() throws Exception {
        assertEquals(strategy.getUrl("foo.wikia.com"), new URL("http://foo.wikia.com/"));
    }

    @Test
    public void testGetUrlDomainNoProtocolApiEndpoint() throws Exception {
        assertEquals(strategy.getUrl("foo.wikia.com/api.php"), new URL("http://foo.wikia.com/api.php"));
    }

    @Test
    public void testGetUrlDomainNoProtocolNewApiEndpoint() throws Exception {
        assertEquals(strategy.getUrl("foo.wikia.com/w/api.php"), new URL("http://foo.wikia.com/w/api.php"));
    }

    @Test
    public void testGetUrlDomainProtocolNewApiEndpoint() throws Exception {
        assertEquals(strategy.getUrl("http://foo.wikia.com/w/api.php"), new URL("http://foo.wikia.com/w/api.php"));
    }

    @Test
    public void testGetUrlDomainCorruptedProtocolNewApiEndpoint() throws Exception {
        assertEquals(strategy.getUrl("http:/foo.wikia.com/w/api.php"), new URL("http://foo.wikia.com/w/api.php"));
    }

    @Test
    public void assumptions() {
        assertEquals("a/".replaceFirst("/","//"), "a//");
        assertEquals(":/".replaceFirst("/","//"), "://");
        assertEquals("a:/".replaceFirst(":/","://"), "a://");
        assertTrue(Pattern.compile("^a:/").matcher("a:/x").find());
        assertTrue(Pattern.compile("^http:/", Pattern.CASE_INSENSITIVE).matcher("http:/xxx.com/asd").find());
    }
}
