package com.ericsson.rda.jaft.storage;

import org.mapdb.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaftStorage {

    private static final Logger logger = LoggerFactory.getLogger(JaftStorage.class);
    private static JaftStorageNamespace nsStorage = new JaftStorageNamespace();
    private static HashMap<String , JaftStorageNamespace> openedNamespace = new HashMap<String , JaftStorageNamespace>();

    public static void open()
    {
        nsStorage.open("system.namespace.db");
    }

    public static void setValue(String namespace, String key, String value)
    {
        if(openedNamespace.containsKey(namespace))
        {
            JaftStorageNamespace namespaceStroage = openedNamespace.get(namespace);
            namespaceStroage.setValue(key, value);
        }
        else
        {
            JaftStorageNamespace namespaceStroage = new JaftStorageNamespace();
            namespaceStroage.open(namespace);
            openedNamespace.put(namespace, namespaceStroage);
            namespaceStroage.setValue(key, value);
        }
    }

    public static String getValue(String namespace, String key)
    {
        if(openedNamespace.containsKey(namespace))
        {
            JaftStorageNamespace namespaceStroage = openedNamespace.get(namespace);
            return namespaceStroage.getValue(key);
        }
        else
        {
            JaftStorageNamespace namespaceStroage = new JaftStorageNamespace();
            namespaceStroage.open(namespace);
            openedNamespace.put(namespace, namespaceStroage);
            return namespaceStroage.getValue(key);
        }
    }

    public static void close()
    {
        //for (HashMap.Entry<String, JaftStorageNamespace> entry : openedNamespace.entrySet())
        for(String namespace : openedNamespace.keySet())
        {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            openedNamespace.get(namespace).close();
        }
        nsStorage.close();
    }


}
