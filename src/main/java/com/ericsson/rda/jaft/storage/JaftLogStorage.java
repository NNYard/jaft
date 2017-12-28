package com.ericsson.rda.jaft.storage;

import com.ericsson.rda.jaft.LogEntry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JaftLogStorage {

    private static Connection con=null;
    //private static Statement stmt=null;
    private static LogEntry lastLog;

    private static final Logger logger = LoggerFactory.getLogger(JaftLogStorage.class);

    public static void open(String db_path)
    {
        try
        {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:" + db_path, "jaft", "jaft" );
            Statement stmt = con.createStatement();
            //stmt.executeUpdate( "DROP TABLE LogEntry" );
            stmt.executeUpdate( "CREATE TABLE IF NOT EXISTS LogEntry ( index int, term int, type varchar(32), data binary)" );
//            stmt.executeUpdate( "INSERT INTO table1 ( user ) VALUES ( 'Claudio' )" );
//            stmt.executeUpdate( "INSERT INTO table1 ( user ) VALUES ( 'Bernasconi' )" );
//
//            ResultSet rs = stmt.executeQuery("SELECT * FROM table1");
//            while( rs.next() )
//            {
//                String name = rs.getString("user");
//                System.out.println( name );
//            }
           stmt.close();
//            con.close();
        }
        catch( Exception e )
        {
            logger.error( e.getMessage() );
        }
    }

    public static void close()
    {
        try {
            con.close();
        }
        catch( Exception e )
        {
            logger.error( e.getMessage() );
        }
    }

    public static void appendLog(LogEntry logEntry)
    {
        try
        {
//            stmt.executeUpdate( String.format("INSERT INTO LogEntry ( index, term, type, data ) VALUES ( %d, %d, '', '%s' )"
//                    , logEntry.getIndex(), logEntry.getTerm(), logEntry.getValue() ));
            PreparedStatement prep = con.prepareStatement(
                    "INSERT INTO LogEntry ( index, term, type, data ) VALUES ( ?, ?, ?, ? )");
            prep.setInt(1, logEntry.getIndex());
            prep.setInt( 2, logEntry.getTerm());
            prep.setString(3, "");
            prep.setBytes(4, logEntry.getValue());
            prep.executeUpdate();
            prep.close();
            JaftLogStorage.setLastLog(logEntry);
        }
        catch( Exception e )
        {
            logger.error( e.getMessage() );
        }
    }

    public static LogEntry getLogEntry(int index)
    {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT index, term, data  FROM LogEntry where index=%d", index));
            LogEntry logEntry = new LogEntry();
            if(rs.next()) {
                logEntry.setIndex(rs.getInt("index"));
                //System.out.println("getLogEntry term=" + rs.getInt("term"));
                logEntry.setTerm(rs.getInt("term"));
                //System.out.println(rs.getString("data"));
                logEntry.setValue(rs.getBytes("data"));
            }
            stmt.close();
            return logEntry;
        }
        catch( Exception e )
        {
            logger.error( e.getMessage() );
        }
        return null;
    }

    public static LogEntry getLastLog() {
        return JaftLogStorage.lastLog;
    }

    private static void setLastLog(LogEntry lastLog) {
        JaftLogStorage.lastLog = lastLog;
    }
}
