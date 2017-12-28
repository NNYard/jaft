package com.ericsson.rda.jaft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by exiango on 8/24/2017.
 */
public class StateMachine {

    private static final Logger logger = LoggerFactory.getLogger(StateMachine.class);

    private static JaftNode server;
    private static int grantedVotes;
    private static int appliedLogs;
    private static int majority;

    public static void init(JaftNode server) {
        StateMachine.server = server;
        majority = JaftContext.getPeers().size() / 2 + 1;
    }

    public static void turnToLeader() {
        logger.info("Become Leader: {}", server.getId());
        server.setRole(Role.Leader);
        grantedVotes = 0;
    }

    public static void turnToCandidate() {
        logger.info("Become Candidate: {}", server.getId());
        server.setRole(Role.Candidate);
        grantedVotes = 0;
    }

    public static void turnToFollower() {
        logger.info("Become Follower: {}", server.getId());
        server.setRole(Role.Follower);
        grantedVotes = 0;
    }

    public static void voteGranted() {
        synchronized (StateMachine.class) {
            grantedVotes++;
        }
    }

    public static void logApplied() {
        synchronized (StateMachine.class) {
            appliedLogs++;
        }
    }

    public static int countVotes() {
        return grantedVotes;
    }

    public static boolean submitLog() {
        if (appliedLogs < majority) {
            appliedLogs = 0;
            return false;
        } else {
            server.setCommitIndex(server.getCommitIndex() + 1);
            appliedLogs = 0;
            return true;
        }
    }
}
