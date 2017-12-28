package com.ericsson.rda.jaft.entity;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public class AppendEntryResponse extends Response{
    private int term;
    private boolean success;

    public AppendEntryResponse(int term, boolean success) {
        this.term = term;
        this.success = success;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "AppendEntryResponse{" +
                "term=" + term +
                ", success=" + success +
                '}';
    }
}
