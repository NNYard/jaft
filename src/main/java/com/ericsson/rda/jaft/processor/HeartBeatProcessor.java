package com.ericsson.rda.jaft.processor;

import com.ericsson.rda.jaft.Jaft;
import com.ericsson.rda.jaft.JaftNode;
import com.ericsson.rda.jaft.Role;
import com.ericsson.rda.jaft.entity.HeartBeatResponse;
import com.ericsson.rda.jaft.entity.Request;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public class HeartBeatProcessor implements Processor {
    private static HeartBeatProcessor instance;
    private HeartBeatProcessor(){

    }
    public static HeartBeatProcessor getInstance(){
        if(instance == null){
            instance = new HeartBeatProcessor();
        }
        return instance;
    }

    public HeartBeatResponse process(Request request, JaftNode node) {
        node.setRole(Role.Follower);
        Jaft.getHeartbeatTimer().stop();
        Jaft.getElectionTimer().restart();
        return new HeartBeatResponse();
    }
}
