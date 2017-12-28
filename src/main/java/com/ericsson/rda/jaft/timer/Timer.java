package com.ericsson.rda.jaft.timer;

import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * This is used to send the heartbeat and request leader election.
 */
public abstract class Timer extends ScheduledThreadPoolExecutor {

    protected ScheduledFuture<?> timer;
    protected int MAX_TIMEOUT;
    protected long timeout;
    protected Random random = new Random();

    public Timer() {
        super(Runtime.getRuntime().availableProcessors());
    }

    public abstract void start();

    public void stop() {
        timer.cancel(false);
    }

    public void restart() {
        stop();
        start();
    }

}
