package com.ericsson.rda.jaft.entity;

/**
 * Created by Eric Cen on 8/25/2017.
 */
public class SetKvResponse extends Response {
    private boolean success;
    private String leaderId;

    public SetKvResponse(boolean success, String leaderId) {
        super.setResponseType(ResponseType.SET_KEY_VALUE);
        this.success = success;
        this.leaderId = leaderId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }
}
