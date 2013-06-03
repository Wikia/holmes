package com.wikia.reader.cache;/**
 * Author: Artur Dwornik
 * Date: 22.05.13
 * Time: 21:28
 */

import com.wikia.reader.util.TmpDirHelper;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class MapdbPermanentCache<Key extends Serializable, Value extends Serializable> implements Cache<Key, Value> {
    private static Logger logger = LoggerFactory.getLogger(MapdbPermanentCache.class);
    static DB db;
    static {
        try {
            db = DBMaker.newFileDB(new File(String.valueOf(TmpDirHelper.getTempPath("__wikia__cache__"))))
                    .closeOnJvmShutdown()
                    .make();
        } catch (IOException e) {
            logger.error("Error while creating cache db.", e);
        }
    }
    private final Map<Key, Value> dbMap;

    public MapdbPermanentCache( String region ) {
        dbMap = db.getHashMap( region );
    }


    @Override
    public Value get(Key key) {
        return dbMap.get(key);
    }

    @Override
    public void set(Key key, Value value) {
        dbMap.put(key,value);
    }
}
