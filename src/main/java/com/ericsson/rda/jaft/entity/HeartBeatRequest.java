package com.ericsson.rda.jaft.entity;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public class HeartBeatRequest extends Request{
    public HeartBeatRequest() {
        super.setType(RequestType.HEARTBEAT);
    }
}
