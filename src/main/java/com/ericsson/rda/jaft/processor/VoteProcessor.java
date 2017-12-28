package com.ericsson.rda.jaft.processor;

import com.ericsson.rda.jaft.JaftNode;
import com.ericsson.rda.jaft.JaftServerHandler;
import com.ericsson.rda.jaft.Role;
import com.ericsson.rda.jaft.entity.Request;
import com.ericsson.rda.jaft.entity.VoteRequest;
import com.ericsson.rda.jaft.entity.VoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public class VoteProcessor implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaftServerHandler.class);
    private static VoteProcessor instance;
    private VoteProcessor(){

    }
    public static VoteProcessor getInstance(){
        if(instance == null){
            instance = new VoteProcessor();
        }
        return instance;
    }

    public VoteResponse process(Request request, JaftNode node) {
        LOGGER.info("Start to process Vote Request.");
        VoteRequest voteRequest = (VoteRequest) request;
        int term = voteRequest.getTerm();
        String candidateId = voteRequest.getCandidateId();
        int lastLogIndex = voteRequest.getLastLogIndex();


        if(term  < node.getCurrentTerm()){
            LOGGER.info("Request Term is smaller than Node Current Term.");
            return new VoteResponse(node.getCurrentTerm(), false);
        } else {
            node.setCurrentTerm(term);
            node.setRole(Role.Follower);
        }
        if((node.getVotedFor() == null || node.getVotedFor().equals(candidateId))
                && node.getCommitIndex() <= lastLogIndex){
            LOGGER.info("Vote Granted.");
            return new VoteResponse(node.getCurrentTerm(), true);
        }
        return new VoteResponse(node.getCurrentTerm(), false);
    }
}
