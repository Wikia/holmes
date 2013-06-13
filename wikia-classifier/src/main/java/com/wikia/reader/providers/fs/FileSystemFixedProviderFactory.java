package com.wikia.reader.providers.fs;

import com.wikia.reader.input.WikiTextChunkParser;
import com.wikia.reader.providers.Provider;
import com.wikia.reader.providers.WikiProvider;
import com.wikia.reader.providers.api.WikiApiTextReader;
import com.wikia.reader.providers.api.WikiFixedIndexReader;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 18:09
 */
public class FileSystemFixedProviderFactory {
    private static Logger logger = Logger.getLogger(FileSystemFixedProviderFactory.class.toString());
    private Path dir;
    private final Iterable<String> index;

    public FileSystemFixedProviderFactory(Path dir, Iterable<String> index) {
        this.dir = dir;
        this.index = index;
    }

    public Provider get() {
        return new WikiProvider(
                  new WikiFixedIndexReader(index)
                , new FileSystemTextReader(dir)
                , new WikiTextChunkParser());
    }
}
