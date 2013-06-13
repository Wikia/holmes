package com.wikia.reader.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.wikia.reader.input.TextChunk;
import com.wikia.reader.providers.Provider;
import com.wikia.reader.providers.fs.FileSystemProviderFactory;
import com.wikia.reader.util.AsyncQueues;
import com.wikia.reader.util.RandomUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 31.03.13
 * Time: 15:25
 */
@Parameters(commandDescription = "Select random pages from dir.")
public class RandomSelectCommand implements Command {
    private static Logger logger = Logger.getLogger(RandomSelectCommand.class.toString());

    @Parameter(names = "-c", description = "number of wikis to select.")
    private Integer count = new Integer(50);
    @Parameter(description = "Paths.", required = true)
    private List<String> paths;

    @Override
    public String getName() {
        return "RandomSelect";
    }

    @Override
    public void execute(AppParams params) {
        try {
            List<TextChunk> chunks = new ArrayList<>();
            for (String pathString: paths) {
                Path path = Paths.get(pathString);
                FileSystemProviderFactory providerFactory = new FileSystemProviderFactory(path);
                Provider provider = providerFactory.get();
                Iterator<TextChunk> iterator = AsyncQueues.synchronize(provider.provide());
                while ( iterator.hasNext() ) {
                    TextChunk textChunk = iterator.next();
                    chunks.add(textChunk);
                    logger.fine(textChunk.getTitle());
                }
            }
            List<TextChunk> selected = RandomUtil.randomTake(chunks, (int) count);
            System.out.print("{\n");
            for (TextChunk textChunk: selected) {
                System.out.print("\""+textChunk.getTitle() + "\": [],\n");
            }
            System.out.print("}\n");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unexpected error", ex);
        }
    }
}
