package com.wikia.classifier.commands;

import com.beust.jcommander.Parameter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerCommand implements Command {
    private static Logger logger = LoggerFactory.getLogger(ServerCommand.class);
    private URI getBaseURI() {
        return URI.create("http://0.0.0.0:9998/");
    }
    public final URI BASE_URI = getBaseURI();

    @Parameter( names = {"-p", "--port"}, required = true, description = ".")
    private int port = 9998;

    @Parameter( names = {"-c", "--classifier"}, required = true, description = "Serialized classifier file name.")
    private String classifierFilePath;

    @Parameter( names = {"-w", "--war"}, required = false, description = "War file with holmes-web application.")
    private String warFilePath = ServerCommand.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/../holmes-web.war";

    @Override
    public String getName() {
        return "Server";
    }

    @Override
    public void execute(AppParams params) {
        try {
            Path path = FileSystems.getDefault().getPath(classifierFilePath);
            if ( !Files.exists(path) ) {
                throw new FileNotFoundException("File at " + path.toAbsolutePath() + " not found");
            }
            System.setProperty("classifierFile", path.toAbsolutePath().toString());

            System.out.print(String.format("Starting server (%s)\n", BASE_URI));
            Server httpServer = startServer();
            System.out.println(String.format("Jersey app started with WADL available at "
                    + "%sapplication.wadl\nTry out %sclassifier\nHit enter to stop it...",
                    BASE_URI, BASE_URI));
            System.in.read();
            httpServer.stop();
        } catch (Exception e) {
            logger.error("Error in http server.", e);
        }
    }

    protected Server startServer() throws Exception {
        Server server = new Server(9998);
        WebAppContext context = new WebAppContext();
        context.setWar( warFilePath );
        context.setParentLoaderPriority(true);
        server.setHandler(context);

        System.out.println("Starting Server!");
        server.start();
        return server;
    }
}
