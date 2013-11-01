package com.wikia.classifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppIntegrationTest {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(AppIntegrationTest.class);

    @org.testng.annotations.Test(enabled = false)
    public void testServer() throws Exception {
        App.main(new String[] {"Server"});
    }
}
