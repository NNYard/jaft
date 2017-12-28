package com.ericsson.rda.jaft.processor;

import com.ericsson.rda.jaft.entity.ResponseType;

/**
 * Created by exiango on 8/24/2017.
 */
public class ResponseProcessorFactory {
    public static ResponseProcessor getResponseProcessor(ResponseType responseType) {
        switch (responseType) {
            case APPEND_ENTRY:
                return AppendEntryResponseProcessor.getInstance();
            case VOTE:
                return VoteResponseProcessor.getInstance();
            case HEARTBEAT:
                return HeartBeatResponseProcessor.getInstance();
            default:
                return new DefaultResponseProcessor();
        }
    }
}
