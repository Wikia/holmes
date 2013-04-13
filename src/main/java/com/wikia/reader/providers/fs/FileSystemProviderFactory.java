package com.wikia.reader.providers.fs;

import com.wikia.reader.input.WikiTextChunkParser;
import com.wikia.reader.providers.Provider;
import com.wikia.reader.providers.WikiProvider;
import com.wikia.reader.providers.WikiProviderFilter;
import com.wikia.reader.providers.api.WikiApiIndexReader;
import com.wikia.reader.providers.api.WikiApiTextReader;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 31.03.13
 * Time: 15:45
 */
public class FileSystemProviderFactory {
    private static Logger logger = Logger.getLogger(FileSystemProviderFactory.class.toString());
    private final Path path;

    public FileSystemProviderFactory(Path path) {
        this.path = path;
    }

    public Provider get() {

        Provider provider = new WikiProvider(new FileSystemIndexReader(path)
                , new FileSystemTextReader(path)
                , new WikiTextChunkParser());
        return new WikiProviderFilter(provider);
    }
}
