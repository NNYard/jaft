package com.ericsson.rda.jaft.entity;

/**
 * Created by Eric Cen on 8/25/2017.
 */
public class SetKeyValueRequest extends Request {
    private String key;
    private String value;
    private String nameSpace;

    public SetKeyValueRequest(String key, String value, String nameSpace) {
        super.setType(RequestType.SET_KEY_VALUE);
        this.key = key;
        this.value = value;
        this.nameSpace = nameSpace;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }
}
