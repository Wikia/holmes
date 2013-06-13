package com.wikia.reader.providers.fs;

import com.wikia.reader.providers.WikiIndexReader;
import com.wikia.reader.util.AsyncQueue;
import com.wikia.reader.util.BasicAsyncQueue;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 31.03.13
 * Time: 15:30
 */
public class FileSystemIndexReader implements WikiIndexReader {
    private static Logger logger = Logger.getLogger(FileSystemIndexReader.class.toString());
    private Path path;
    private IOFileFilter fileFilter, dirFilter;

    public FileSystemIndexReader(Path path, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        this.path = path;
        this.fileFilter = fileFilter;
        this.dirFilter = dirFilter;
    }

    public FileSystemIndexReader(Path path) {
        this(path
                , new AbstractFileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return true;
                    }
                }, new AbstractFileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return false;
                    }
                }
        );
    }

    @Override
    public AsyncQueue<String> getIndex() throws IOException {
        BasicAsyncQueue<String> queue = new BasicAsyncQueue<>();
        Collection<File> files = FileUtils.listFiles(new File(String.valueOf(path)), fileFilter, dirFilter);

        for (File file : files) {
            String name = URLDecoder.decode(file.getName(), "UTF-8");
            queue.pushOne(name);
        }
        queue.close();
        return queue;
    }
}
