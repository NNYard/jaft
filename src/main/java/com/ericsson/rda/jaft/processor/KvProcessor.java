package com.ericsson.rda.jaft.processor;

import com.ericsson.rda.jaft.JaftNode;
import com.ericsson.rda.jaft.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Eric Cen on 8/25/2017.
 */
public class KvProcessor implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(KvProcessor.class);
    private static KvProcessor instance;
    private KvProcessor(){

    }
    public static KvProcessor getInstance(){
        if(instance == null){
            instance = new KvProcessor();
        }
        return instance;
    }
    public Response process(Request request, JaftNode node) {
        RequestType requestType = request.getType();
        LOGGER.info(String.format("LeadID=%s, NodeID=%s.", node.getLeaderId(), node.getId()));
        switch(requestType){
            case GET_KEY_VALUE:
                LOGGER.info("Receive request to Get key value.");
                if(node.getLeaderId() != node.getId()){
                    LOGGER.info("This is not the Leader Node. Please resend the request to Leader Node.");
                    return new GetKvResponse(null, node.getLeaderId());
                }
                GetKeyValueRequest getKvReq = (GetKeyValueRequest)request;
                LOGGER.info("Going to Get KeyValue of: " + getKvReq.getNameSpace() + "/" + getKvReq.getKey());
                String value = node.getKeyValue(getKvReq.getNameSpace(),getKvReq.getKey());
                LOGGER.info("Get value: " + value);
                return new GetKvResponse(value, node.getId());
            case SET_KEY_VALUE:
                LOGGER.info("Receive request to Set key value.");
                if(node.getLeaderId() != node.getId()){
                    LOGGER.info("This is not the Leader Node. Please resend the request to Leader Node.");
                    return new SetKvResponse(false, node.getLeaderId());
                }
                SetKeyValueRequest setKvReq = (SetKeyValueRequest)request;
                LOGGER.info("Going to Set KeyValue of: " + setKvReq.getNameSpace() + "/"
                        + setKvReq.getKey() + ":" + setKvReq.getValue());
                node.setKeyValue(setKvReq.getNameSpace(), setKvReq.getKey(), setKvReq.getValue());
                LOGGER.info("Value set.");
                return new SetKvResponse(true, node.getId());

        }
        return null;
    }
}
