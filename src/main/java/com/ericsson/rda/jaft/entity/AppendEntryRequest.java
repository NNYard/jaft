package com.ericsson.rda.jaft.entity;

import com.ericsson.rda.jaft.LogEntry;

import java.util.Arrays;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public class AppendEntryRequest extends Request {
    private int term;
    private String leaderId;
    private int prevLogIndex;
    private int prevLogTerm;
    private LogEntry[] logEntries;
    private int leaderCommit;

    public AppendEntryRequest(int term, String leaderId, int prevLogIndex,
                              int prevLogTerm,
                              LogEntry[] logEntries, int leaderCommit) {
        this.term = term;
        this.leaderId = leaderId;
        this.prevLogIndex = prevLogIndex;
        this.prevLogTerm = prevLogTerm;
        this.logEntries = logEntries;
        this.leaderCommit = leaderCommit;
        this.setType(RequestType.APPEND_ENTRY);
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public int getPrevLogIndex() {
        return prevLogIndex;
    }

    public void setPrevLogIndex(int prevLogIndex) {
        this.prevLogIndex = prevLogIndex;
    }

    public int getPrevLogTerm() {
        return prevLogTerm;
    }

    public void setPrevLogTerm(int prevLogTerm) {
        this.prevLogTerm = prevLogTerm;
    }

    public LogEntry[] getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(LogEntry[] logEntries) {
        this.logEntries = logEntries;
    }

    public int getLeaderCommit() {
        return leaderCommit;
    }

    public void setLeaderCommit(int leaderCommit) {
        this.leaderCommit = leaderCommit;
    }

    @Override
    public String toString() {
        return "AppendEntryRequest{" +
                "term=" + term +
                ", leaderId=" + leaderId +
                ", prevLogIndex=" + prevLogIndex +
                ", prevLogTerm=" + prevLogTerm +
                ", logEntries=" + Arrays.toString(logEntries) +
                ", leaderCommit=" + leaderCommit +
                '}';
    }
}
