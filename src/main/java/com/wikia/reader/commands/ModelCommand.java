package com.wikia.reader.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wikia.reader.input.TextChunk;
import com.wikia.reader.providers.Provider;
import com.wikia.reader.providers.fs.FileSystemFixedProviderFactory;
import com.wikia.reader.weka.*;
import com.wikia.reader.util.AsyncQueues;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 31.03.13
 * Time: 22:38
 */
@Parameters(commandDescription = "Creates model based on index file and runs it against wiki dump.")
public class ModelCommand implements Command {
    private static Logger logger = Logger.getLogger(ModelCommand.class.toString());

    @Parameter(names = {"-i", "--index"},description = "Index file.", required = true)
    private String indexFile;

    @Parameter(names = {"-d", "--dir"},description = "Directory.", required = true)
    private String dir;
    private Iterable<String> index;

    @Override
    public String getName() {
        return "Model";
    }

    @Override
    public void execute(AppParams params) {
        try {
            FileSystemFixedProviderFactory factory =
                    new FileSystemFixedProviderFactory(Paths.get(dir), getIndex());
            Provider provider = factory.get();
            List<TextChunk> chunks = AsyncQueues.toList(provider.provide());
            KNNModelFactory modelFactory = new LinearSearchKNNModelFactory();
            KNNModel model = modelFactory.get(chunks, getPredefinedProperties());
            for(int i=0; i<10; i++) {
                TextChunk chunk = chunks.get(i);
                List<String> allProperties = model.getAllProperties(chunk);
                logger.info( chunk.getTitle() + " : " + allProperties.toString());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Fatal exception.", e);
        }
    }

    public Iterable<String> getIndex() throws IOException {
        Map<String, List<String>> properties = getPredefinedProperties();
        return properties.keySet();
    }

    private Map<String, List<String>> getPredefinedProperties() throws IOException {
        JsonElement jsonElement = new JsonParser().parse(FileUtils.readFileToString(new File(indexFile)));
        Map<String, List<String>> properties = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry: jsonElement.getAsJsonObject().entrySet()) {
            JsonElement element = entry.getValue().getAsJsonArray();
            ArrayList<String> tagArray = new ArrayList<>();
            for (JsonElement tag: element.getAsJsonArray()) {
                tagArray.add(tag.getAsString());
            }
            properties.put(entry.getKey(), tagArray);
        }
        return properties;
    }
}
