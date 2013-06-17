package com.wikia.reader.text.service;/**
 * Author: Artur Dwornik
 * Date: 13.04.13
 * Time: 20:29
 */

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class ClassifierMain {
    private static Logger logger = LoggerFactory.getLogger(ClassifierMain.class);
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0/").port(9998).build();
    }
    public static final URI BASE_URI = getBaseURI();
    protected static HttpServer startServer() throws IOException {
        System.out.println("Starting grizzly...");
        ResourceConfig rc = new PackagesResourceConfig("com.wikia.reader.text.service");
        ClassificationResource.getClassifierManager();
        rc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
    }

    public static void main(String[] args) throws IOException {
        System.out.print(String.format("Starting server (%s)\n", BASE_URI));
        HttpServer httpServer = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                  + "%sapplication.wadl\nTry out %shelloworld\nHit enter to stop it...",
                  BASE_URI, BASE_URI));
        System.in.read();
        httpServer.stop();
    }
}
