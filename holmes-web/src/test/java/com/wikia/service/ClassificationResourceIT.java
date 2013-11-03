package com.wikia.service;


import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import javax.ws.rs.core.Response;
import java.io.InputStream;

import static org.testng.Assert.*;

public class ClassificationResourceIT {
    private static Logger logger = LoggerFactory.getLogger(ClassificationResourceIT.class);
    private static String endpointUrl;

    @BeforeClass
    public static void beforeClass() {
        endpointUrl = System.getProperty("service.url");
        assertNotNull(endpointUrl, "endpoint url is not defined.");
        logger.info("ENDPOINT: " + endpointUrl);
    }

    @Test
    public void testGetClassificationsShortUrl() throws Exception {
        WebClient client = WebClient.create(endpointUrl + "/classifications/yugioh/Astral");
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream) r.getEntity());
        assertNotNull(value);
        // TODO: deserialize and read
    }

    @Test
    public void testGetClassificationsFullUrl() throws Exception {
        WebClient client = WebClient.create(endpointUrl + "/classifications/http://yugioh.wikia.com/api.php/Astral");
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream) r.getEntity());
        assertNotNull(value);
        // TODO: deserialize and read
    }
}
