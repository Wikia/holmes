package com.wikia.reader.util;/**
 * Author: Artur Dwornik
 * Date: 23.04.13
 * Time: 13:59
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TmpDirHelper {
    private static Logger logger = LoggerFactory.getLogger(TmpDirHelper.class);
    private static Path tempPath;

    private static Path create() throws IOException {
        Path path = Paths.get(System.getProperty("user.dir"), "cache");
        if ( !Files.exists(path) ) {
            Files.createDirectory(path);
        }
        return path;
    }

    public static Path get() throws IOException {
        if ( tempPath == null ) {
            tempPath = create();
        }
        return tempPath;
    }

    public static Path getTempPath( String filename ) throws IOException {
        return Paths.get( get().toString(), filename );
    }
}
