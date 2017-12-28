package com.ericsson.rda.jaft.processor;

import com.ericsson.rda.jaft.StateMachine;
import com.ericsson.rda.jaft.entity.Response;
import com.ericsson.rda.jaft.entity.VoteResponse;

/**
 * Created by exiango on 8/24/2017.
 */
public class VoteResponseProcessor implements ResponseProcessor {
    private static VoteResponseProcessor ourInstance = new VoteResponseProcessor();

    public void process(Response response) {
        VoteResponse voteResponse = (VoteResponse) response;
        if (voteResponse.isVoteGranted()) {
            StateMachine.voteGranted();
        }
    }

    public static VoteResponseProcessor getInstance() {
        return ourInstance;
    }

    private VoteResponseProcessor() {
    }


}
