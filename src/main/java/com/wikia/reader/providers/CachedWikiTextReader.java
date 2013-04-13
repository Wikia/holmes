package com.wikia.reader.providers;
/**
 * Author: Artur Dwornik
 * Date: 08.04.13
 * Time: 21:23
 */

import com.wikia.reader.util.ThreadPools;
import org.apache.http.concurrent.FutureCallback;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class CachedWikiTextReader implements WikiTextReader {
    private static Logger logger = LoggerFactory.getLogger(CachedWikiTextReader.class);
    DB db = DBMaker.newFileDB(new File("/tmp/__wikia_reader_cache"))
            .closeOnJvmShutdown()
            .make();
    private static ExecutorService executorService = ThreadPools.get();

    private final WikiTextReader wikiTextReader;
    private BTreeMap<String, String> cache;

    public CachedWikiTextReader(WikiTextReader wikiTextReader, String domainName) {
        this.wikiTextReader = wikiTextReader;
        cache = db.getTreeMap(domainName);
    }

    @Override
    public void getWikiText(final String title, final FutureCallback<String> callback) throws IOException {
        if(cache.containsKey(title)) {
            final String val = cache.get(title);
            logger.info("take " + title + " from cache.");
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    callback.completed(val);
                }
            });
        } else {
            logger.info("take " + title + " miss.");
            wikiTextReader.getWikiText(title, new StringFutureCallback(title, callback));
        }
    }

    private class StringFutureCallback implements FutureCallback<String> {
        private final String title;
        private final FutureCallback<String> callback;

        public StringFutureCallback(String title, FutureCallback<String> callback) {
            this.title = title;
            this.callback = callback;
        }

        @Override
        public void completed(String s) {
            cache.put(title, s);
            db.commit();
            callback.completed(s);
        }

        @Override
        public void failed(Exception e) {
            callback.failed(e);
        }

        @Override
        public void cancelled() {
            callback.cancelled();
        }
    }
}
