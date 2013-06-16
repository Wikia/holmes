package com.wikia.reader;/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 11:14
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppIntegrationTest {
    private static Logger logger = LoggerFactory.getLogger(AppIntegrationTest.class);

    @org.testng.annotations.Test()
    public void testServer() throws Exception {
        App.main(new String[] {"Server"});
    }
}
