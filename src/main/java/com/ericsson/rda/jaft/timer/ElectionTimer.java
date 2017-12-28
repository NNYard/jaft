package com.ericsson.rda.jaft.timer;

import com.ericsson.rda.jaft.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ElectionTimer extends Timer {

    private static final Logger logger = LoggerFactory.getLogger(ElectionTimer.class);

    private JaftNode server;

    public ElectionTimer(JaftNode server) {
        super();
        this.server = server;
        this.MAX_TIMEOUT = 1500;
    }

    public void start() {
        timeout = 1000 + random.nextInt(MAX_TIMEOUT);
        timer = this.schedule(new Callable<Void>() {
            public Void call() {
                // TODO: implement logic here. Robin
                logger.info("Leader election triggered");
                logger.info("Become candidate.");
                server.setRole(Role.Candidate);
                int term = server.getCurrentTerm();
                server.setCurrentTerm(term + 1);
                StateMachine.voteGranted();
                server.requestVote();
                int votes = StateMachine.countVotes();
                if (votes < JaftContext.getPeers().size() / 2 + 1) {
                    restart();
                } else {
                    logger.info("Got majority votes, become leader");
                    server.setRole(Role.Leader);
                    server.setLeaderId(server.getId());
                    logger.info(String.format("Select Leader(%s): node_id=%s index=%s, term=%d", server.getLeaderId(), server.getId(), server.getIndex(), server.getCurrentTerm()));
                    Jaft.getHeartbeatTimer().start();
                }
                return null;
            }
        }, timeout, TimeUnit.MILLISECONDS);
    }
}
