package com.ericsson.rda.jaft.entity;

/**
 * Created by Eric Cen on 8/25/2017.
 */
public class GetKvResponse extends Response {
    private String value;

    private String leaderId;

    public GetKvResponse(String value, String leaderId) {
        super.setResponseType(ResponseType.GET_KEY_VALUE);
        this.value = value;
        this.leaderId = leaderId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }
}
