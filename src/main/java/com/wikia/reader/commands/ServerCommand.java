package com.wikia.reader.commands;/**
 * Author: Artur Dwornik
 * Date: 23.04.13
 * Time: 23:03
 */

import com.beust.jcommander.Parameter;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.wikia.reader.input.TextChunk;
import com.wikia.reader.providers.Provider;
import com.wikia.reader.providers.fs.FileSystemProviderFactory;
import com.wikia.reader.text.service.ClassificationResource;
import com.wikia.reader.util.AsyncQueues;
import com.wikia.reader.util.RandomUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class ServerCommand implements Command {
    private static Logger logger = LoggerFactory.getLogger(ServerCommand.class);
    private URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0/").port(9998).build();
    }
    public final URI BASE_URI = getBaseURI();

    @Override
    public String getName() {
        return "Server";
    }

    @Override
    public void execute(AppParams params) {
        try {
            System.out.print(String.format("Starting server (%s)\n", BASE_URI));
            HttpServer httpServer = startServer();
            System.out.println(String.format("Jersey app started with WADL available at "
                    + "%sapplication.wadl\nTry out %sclassifyer\nHit enter to stop it...",
                    BASE_URI, BASE_URI));
            System.in.read();
            httpServer.stop();
        } catch (IOException e) {
            logger.error("Error in http server.", e);
        }
    }

    protected HttpServer startServer() throws IOException {
        System.out.println("Starting grizzly...");
        ResourceConfig rc = new PackagesResourceConfig("com.wikia.reader.text.service");
        ClassificationResource.getClassifierManager();
        rc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
    }
}
