package com.ericsson.rda.jaft.processor;

import com.ericsson.rda.jaft.entity.RequestType;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public class ProcessorFactory {
    public static Processor getProcessor(RequestType requestType){
        switch(requestType){
            case HEARTBEAT:
                return HeartBeatProcessor.getInstance();
            case VOTE:
                return VoteProcessor.getInstance();
            case APPEND_ENTRY:
                return AppendEntryProcessor.getInstance();
            case GET_KEY_VALUE:
                return KvProcessor.getInstance();
            case SET_KEY_VALUE:
                return KvProcessor.getInstance();
            default:
                return new DefaultProcessor();
        }
    }
}
