package com.ericsson.rda.jaft;

import com.ericsson.rda.jaft.entity.AppendEntryRequest;
import com.ericsson.rda.jaft.entity.HeartBeatRequest;
import com.ericsson.rda.jaft.entity.VoteRequest;
import com.ericsson.rda.jaft.storage.JaftStorage;
import com.ericsson.rda.jaft.storage.JaftLogStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public class JaftNode {

    private static final Logger logger = LoggerFactory.getLogger(JaftNode.class);

    private Role role = Role.Follower;

    private int currentTerm;

    private String votedFor;

    private List<LogEntry> logEntries = new ArrayList<LogEntry>();

    private int commitIndex;

    private int lastApplied;

    private int[] nextIndex;

    private int[] matchIndex;

    private String index;

    private String leaderId;

    private String id;

    public JaftNode() throws UnknownHostException {
        this.id = InetAddress.getLocalHost().getHostName();
    }

    /**
     * Request vote from other server.
     */
    public void requestVote() {
        logger.info("Going to request vote...");
        System.out.println("Going to request vote...");
        Map<String, JaftNettyClient> clients = JaftContext.getClients();
        for (JaftNettyClient client : clients.values()) {
            logger.info("Send request to server: {}", client.getHost());
            LogEntry logEntry = JaftLogStorage.getLastLog();
            int term = 0;
            if (logEntry != null) {
                term = logEntry.getTerm();
            }
            client.sendRequest(new VoteRequest(currentTerm, id, lastApplied, term));
        }
    }

    /**
     * Attach entries to other server. Also used for heart beat.
     */
    public void attachEntry(LogEntry[] logEntries) {
        // TODO: send rpc here. Robin
        logger.info("Going to append entries...");
        Map<String, JaftNettyClient> clients = JaftContext.getClients();
        for (JaftNettyClient client : clients.values()) {
            LogEntry lastLog = JaftLogStorage.getLastLog();

            int lastLogTerm = 0;
            int lastCommit = 0;
            if (lastLog != null) {
                lastLogTerm = lastLog.getTerm();
                lastCommit = lastLog.getIndex();
            }
            if (logEntries == null) {
                client.sendRequest(new HeartBeatRequest());
            } else {
                client.sendRequest(new AppendEntryRequest(currentTerm, id, lastApplied, lastLogTerm, logEntries, lastCommit));
            }
        }
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(int currentTerm) {
        this.currentTerm = currentTerm;
    }

    public String getVotedFor() {
        return votedFor;
    }

    public void setVotedFor(String votedFor) {
        this.votedFor = votedFor;
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    public int getCommitIndex() {
        return commitIndex;
    }

    public void setCommitIndex(int commitIndex) {
        this.commitIndex = commitIndex;
    }

    public int getLastApplied() {
        return lastApplied;
    }

    public void setLastApplied(int lastApplied) {
        this.lastApplied = lastApplied;
    }

    public int[] getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int[] nextIndex) {
        this.nextIndex = nextIndex;
    }

    public int[] getMatchIndex() {
        return matchIndex;
    }

    public void setMatchIndex(int[] matchIndex) {
        this.matchIndex = matchIndex;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyValue(String nameSpace, String key){
        return JaftStorage.getValue(nameSpace, key);
    }

    public void setKeyValue(String nameSpace, String key, String value){
        JaftStorage.setValue(nameSpace, key, value);
    }

    @Override
    public String toString() {
        return "JaftNode{" +
                "role=" + role +
                ", currentTerm=" + currentTerm +
                ", votedFor='" + votedFor + '\'' +
                ", logEntries=" + logEntries +
                ", commitIndex=" + commitIndex +
                ", lastApplied=" + lastApplied +
                ", nextIndex=" + Arrays.toString(nextIndex) +
                ", matchIndex=" + Arrays.toString(matchIndex) +
                ", index='" + index + '\'' +
                '}';
    }
}
