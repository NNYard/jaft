package com.ericsson.rda.jaft;

import com.ericsson.rda.jaft.JaftContext;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class TestJaftContext {

    @Test
    public void testLoadConfiguration() {

        JaftContext.loadConfiguration("./src/test/resources/config.json");

        assertTrue(JaftContext.getLocalPort() == 9500);
        assertTrue(JaftContext.getLocalAddress().equals("127.0.0.1") == true);
    }
}
