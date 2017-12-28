package com.ericsson.rda.jaft.storage;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

public class JaftStorageNamespace {

    private DB db = null;
    private ConcurrentMap map = null;

    void open(String namespace)
    {
        db = DBMaker.fileDB(namespace).closeOnJvmShutdown().make();
        map = db.hashMap("map").createOrOpen();
    }

    void close()
    {
        if(null != db)
        {
            db.close();
        }
    }

    void setValue(String key, String value)
    {
        map.put(key, value);
    }

    String getValue(String key)
    {
        return (String) map.get(key);
    }

}
