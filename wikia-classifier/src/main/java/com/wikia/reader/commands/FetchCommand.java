package com.wikia.reader.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.wikia.reader.input.TextChunk;
import com.wikia.reader.providers.Provider;
import com.wikia.reader.providers.api.WikiApiProviderFactory;
import com.wikia.reader.util.AsyncQueue;
import com.wikia.reader.util.AsyncQueues;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 30.03.13
 * Time: 17:41
 */
@Parameters(commandDescription = "Fetch documents into files.")
public class FetchCommand implements Command {
    private static Logger logger = Logger.getLogger(FetchCommand.class.toString());

    @Parameter(required = true)
    private List<String> urls;

    @Parameter(names = "--outDir")
    private String dirName = "wiki";

    @Override
    public String getName() {
        return "fetch";
    }

    @Override
    public void execute(AppParams params) {
        try {
            ensureDir(dirName);
            for(String url: urls) {
                Provider provider = new WikiApiProviderFactory(url).get();
                AsyncQueue<TextChunk> queue = provider.provide();
                Iterator<TextChunk> iterator = AsyncQueues.synchronize(queue);
                while( iterator.hasNext() ) {
                    TextChunk textChunk = iterator.next();
                    logger.info("received: " + textChunk.getTitle());
                    writeChunkToFile(textChunk);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unexpected exception.", ex);
        }
    }

    private void writeChunkToFile(TextChunk textChunk) throws IOException {
        String filename = java.net.URLEncoder.encode( textChunk.getTitle(), "UTF-8" );
        Path path = Paths.get(dirName, filename);
        FileUtils.write(path.toFile(), textChunk.getWikiText());
    }

    private void ensureDir(String dirName) throws IOException {
        Files.createDirectories(Paths.get(dirName));
    }
}
