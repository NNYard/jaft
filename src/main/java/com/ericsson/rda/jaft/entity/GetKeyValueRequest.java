package com.ericsson.rda.jaft.entity;

/**
 * Created by Eric Cen on 8/25/2017.
 */
public class GetKeyValueRequest extends Request {
    private String nameSpace;
    private String key;
    private String leaderId;

    public GetKeyValueRequest(String nameSpace, String key, String leaderId) {
        this.nameSpace = nameSpace;
        this.leaderId = leaderId;
        super.setType(RequestType.GET_KEY_VALUE);
        this.key = key;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }
}
