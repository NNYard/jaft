package com.ericsson.rda.jaft.storage;

import com.ericsson.rda.jaft.JaftNettyServer;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public class JaftNettyMockServer {
    private JaftNettyServer server;

    public static void main(String... args) throws InterruptedException {
        JaftNettyServer server = new JaftNettyServer(9394);
        server.run();
    }
}
