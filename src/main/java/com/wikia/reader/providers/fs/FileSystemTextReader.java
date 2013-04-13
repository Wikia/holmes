package com.wikia.reader.providers.fs;

import com.wikia.reader.providers.WikiTextReader;
import com.wikia.reader.util.FileUtils;
import org.apache.http.concurrent.FutureCallback;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 31.03.13
 * Time: 15:49
 */
public class FileSystemTextReader implements WikiTextReader {
    private static Logger logger = Logger.getLogger(FileSystemTextReader.class.toString());
    private Path rootPath;

    public FileSystemTextReader(Path rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public void getWikiText(String title, FutureCallback<String> callback) throws IOException {
        try {
            Path file = Paths.get(rootPath.toAbsolutePath().toString()
                    , URLEncoder.encode(title, "UTF-8"));
            FileUtils.readAsString(file, callback);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while reading file.", e);
            callback.failed(e);
            throw e;
        }
    }
}
