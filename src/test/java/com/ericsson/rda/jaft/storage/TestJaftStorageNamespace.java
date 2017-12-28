package com.ericsson.rda.jaft.storage;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;


public class TestJaftStorageNamespace {

    @Test
    public void testGetAndSetKeyValue() {

        HashMap<String, String> mapNodeHost = new HashMap<String, String>();

        JaftStorageNamespace nsStorage = new JaftStorageNamespace();
        nsStorage.open("./target/test.db");
        nsStorage.setValue("hello", "world");
        String value = nsStorage.getValue("hello");
        nsStorage.close();

        assertTrue(value.equals("world") == true);
    }

}