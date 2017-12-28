package com.ericsson.rda.jaft.timer;

import com.ericsson.rda.jaft.Jaft;
import com.ericsson.rda.jaft.JaftNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class HeartbeatTimer extends Timer {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatTimer.class);

    private JaftNode server;

    public HeartbeatTimer(JaftNode server) {
        super();
        this.server = server;
        this.MAX_TIMEOUT = 1000;
    }

    public void start() {
        timeout = MAX_TIMEOUT;
        timer = this.schedule(new Callable<Void>() {
            public Void call() {
                logger.info("Send heartbeat rpc.");
                server.attachEntry(null);
                Jaft.getHeartbeatTimer().start();
                return null;
            }
        }, timeout, TimeUnit.MILLISECONDS);

    }

    @Override
    public void stop() {
        if (timer != null)
            timer.cancel(false);
    }
}
