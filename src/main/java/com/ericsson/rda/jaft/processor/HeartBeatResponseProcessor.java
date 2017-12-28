package com.ericsson.rda.jaft.processor;

import com.ericsson.rda.jaft.entity.Response;

/**
 * Created by exiango on 8/24/2017.
 */
public class HeartBeatResponseProcessor implements ResponseProcessor {

    private static HeartBeatResponseProcessor ourInstance = new HeartBeatResponseProcessor();

    public void process(Response response) {

    }

    public static HeartBeatResponseProcessor getInstance() {
        return ourInstance;
    }

    private HeartBeatResponseProcessor() {
    }
}
