package com.ericsson.rda.jaft.processor;

import com.ericsson.rda.jaft.JaftNode;
import com.ericsson.rda.jaft.entity.Request;
import com.ericsson.rda.jaft.entity.Response;

/**
 * Created by Eric Cen on 8/24/2017.
 */
public interface Processor {
    Response process(Request request, JaftNode node);
}
