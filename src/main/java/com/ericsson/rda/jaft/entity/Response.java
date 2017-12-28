package com.ericsson.rda.jaft.entity;

import java.io.Serializable;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public abstract class Response implements Serializable {
    private ResponseType responseType;

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
}
