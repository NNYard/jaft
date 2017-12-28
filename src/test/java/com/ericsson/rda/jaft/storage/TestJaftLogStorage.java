package com.ericsson.rda.jaft.storage;

import com.ericsson.rda.jaft.JaftContext;
import com.ericsson.rda.jaft.LogEntry;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class TestJaftLogStorage {

    @Test
    public void testAppendLog() {

        JaftLogStorage.open("./target/test_log_storage.db");

        LogEntry logEntry = new LogEntry();

        int index = 0;
        int term = 1;
        String data = "testAppendLog";

        logEntry.setIndex(index);
        logEntry.setTerm(term);
        logEntry.setValue(data.getBytes());

        System.out.println("logEntry data=" + new String(logEntry.getValue()));

        JaftLogStorage.appendLog(logEntry);

        LogEntry logEntrySearch = JaftLogStorage.getLogEntry(index);


        assertTrue(logEntrySearch.getIndex() == index);
        System.out.println("logEntrySearch term=" + Integer.toString(logEntrySearch.getTerm()));
        assertTrue(logEntrySearch.getTerm() == term);
        System.out.println("logEntrySearch data=" + new String(logEntrySearch.getValue()));
        assertTrue(data.equals(new String(logEntrySearch.getValue())) == true);
        JaftLogStorage.close();

//
    }
}
