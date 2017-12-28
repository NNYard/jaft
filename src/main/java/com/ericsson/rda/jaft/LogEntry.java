package com.ericsson.rda.jaft;

/**
 * Created by exiango on 8/24/2017.
 */
public class LogEntry {
    private int index;
    private int term;
    private byte[] value;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}
