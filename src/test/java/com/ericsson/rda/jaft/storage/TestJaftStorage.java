package com.ericsson.rda.jaft.storage;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestJaftStorage {

    @Test
    public void testGetAndSetKeyValue() {

        JaftStorage.open();
        JaftStorage.setValue("myspace", "hello", "world");
        String value = JaftStorage.getValue("myspace", "hello");
        JaftStorage.close();

        assertTrue(value.equals("world") == true);


    }
}
