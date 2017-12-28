package com.ericsson.rda.jaft.processor;

import com.ericsson.rda.jaft.StateMachine;
import com.ericsson.rda.jaft.entity.AppendEntryResponse;
import com.ericsson.rda.jaft.entity.Response;

/**
 * Created by exiango on 8/24/2017.
 */
public class AppendEntryResponseProcessor implements ResponseProcessor {
    private static AppendEntryResponseProcessor ourInstance = new AppendEntryResponseProcessor();

    public void process(Response response) {
        AppendEntryResponse appendEntryResponse = (AppendEntryResponse) response;
        if (appendEntryResponse.isSuccess()) {
            StateMachine.logApplied();
        }
    }

    public static AppendEntryResponseProcessor getInstance() {
        return ourInstance;
    }

    private AppendEntryResponseProcessor() {
    }

}
