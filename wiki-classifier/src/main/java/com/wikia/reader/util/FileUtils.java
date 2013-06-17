package com.wikia.reader.util;

import org.apache.http.concurrent.FutureCallback;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 31.03.13
 * Time: 17:53
 */
public class FileUtils {
    private static Logger logger = Logger.getLogger(FileUtils.class.toString());

    public static Future<String> readAsString(Path path, final FutureCallback<String> callback) throws IOException {
        final FutureImpl<String> stringFuture = new FutureImpl<>();
        final AsyncFile asyncFile = new AsyncFile(path);
        asyncFile.start(new FutureCallback<String>() {
            @Override
            public void completed(String s) {
                stringFuture.done(s);
                callback.completed(s);
            }

            @Override
            public void failed(Exception e) {
                stringFuture.failed(e);
                callback.failed(e);
            }

            @Override
            public void cancelled() {
                stringFuture.cancelled();
                callback.cancelled();
            }
        });
        return stringFuture;
    }
}
