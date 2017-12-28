package com.ericsson.rda.jaft.entity;

import java.io.Serializable;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public abstract class Request implements Serializable{
    private RequestType type;

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }
}
