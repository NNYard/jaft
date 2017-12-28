package com.ericsson.rda.jaft.processor;

import com.ericsson.rda.jaft.JaftNode;
import com.ericsson.rda.jaft.JaftServerHandler;
import com.ericsson.rda.jaft.LogEntry;
import com.ericsson.rda.jaft.Role;
import com.ericsson.rda.jaft.entity.AppendEntryRequest;
import com.ericsson.rda.jaft.entity.AppendEntryResponse;
import com.ericsson.rda.jaft.entity.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public class AppendEntryProcessor implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaftServerHandler.class);
    private static AppendEntryProcessor instance;
    private AppendEntryProcessor(){

    }

    public static AppendEntryProcessor getInstance(){
        if(instance == null){
            instance = new AppendEntryProcessor();
        }
        return instance;
    }

    public AppendEntryResponse process(Request request, JaftNode node) {
        LOGGER.info("Start to process Append Entry Request.");
        AppendEntryRequest appendEntryRequest = (AppendEntryRequest)request;
        int term = appendEntryRequest.getTerm();
        if(term < node.getCurrentTerm()){
            return new AppendEntryResponse(node.getCurrentTerm(), false);
        }
        node.setCurrentTerm(term);
        node.setRole(Role.Follower);
        int prevLogIndex = appendEntryRequest.getPrevLogIndex();
        int prevLogTerm = appendEntryRequest.getPrevLogTerm();
        LogEntry logEntry = node.getLogEntries().get(prevLogIndex);
        if(logEntry.getTerm() != prevLogTerm){
            return new AppendEntryResponse(node.getCurrentTerm(), false);
        }
        LogEntry[] requestedLogEntries = appendEntryRequest.getLogEntries();
        for(LogEntry entry : requestedLogEntries){
            node.getLogEntries().add(entry);
        }
        node.setLeaderId(appendEntryRequest.getLeaderId());
        return new AppendEntryResponse(node.getCurrentTerm(), true);

    }
}
