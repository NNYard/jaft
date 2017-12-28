package com.ericsson.rda.jaft.storage;

import com.ericsson.rda.jaft.JaftNettyClient;
import com.ericsson.rda.jaft.entity.VoteRequest;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public class JaftNettyMockClient {
    public static void main(String... args) throws InterruptedException {
        JaftNettyClient client = new JaftNettyClient("localhost", 9394);
        client.start();
        VoteRequest request = new VoteRequest(1, "testCandidate", 1,1);
        client.sendRequest(request);
    }
}
