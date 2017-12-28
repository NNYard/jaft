package com.ericsson.rda.jaft;

import com.ericsson.rda.jaft.storage.JaftLogStorage;
import com.ericsson.rda.jaft.storage.JaftStorage;
import com.ericsson.rda.jaft.timer.ElectionTimer;
import com.ericsson.rda.jaft.timer.HeartbeatTimer;
import org.apache.commons.cli.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//jaft --config=./config.json --server start
//jaft --config=./config.json --client start
//jaft --config=./config.json --namespace=default set --key=x --value=1
//jaft --config=./config.json --namespace=defalut get --key=x

public class Jaft {

    private static final Logger logger = LoggerFactory.getLogger(Jaft.class);
    private static ElectionTimer electionTimer = new ElectionTimer(JaftContext.getNode());
    private static HeartbeatTimer heartbeatTimer = new HeartbeatTimer(JaftContext.getNode());

    public static void main( String[] args ) throws Exception {

        // create the options
        logger.info("starting jaft...");
        Options options = new Options();

        options.addOption( "s", "server", false, "start with server mode." );
        options.addOption( "c", "client", false, "start with client mode." );
        options.addOption( OptionBuilder.withLongOpt( "config" )
                .withDescription( "configuration file for Jaft" )
                .hasArg()
                .withArgName("CONFIG")
                .create() );
        options.addOption( OptionBuilder.withLongOpt( "namespace" )
                .withDescription( "namespace for key and value" )
                .hasArg()
                .withArgName("NAMESPACE")
                .create() );
        options.addOption( OptionBuilder.withLongOpt( "key" )
                .withDescription( "key" )
                .hasArg()
                .withArgName("KEY")
                .create() );

        options.addOption( OptionBuilder.withLongOpt( "value" )
                .withDescription( "value" )
                .hasArg()
                .withArgName("VALUE")
                .create() );

        // create the parser
        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        String subCommand = "";
        try {
            // parse the command line arguments
            line = parser.parse( options, args );
            System.out.println("args: " + line.getArgList().get(0));
            subCommand = line.getArgList().get(0);

//            if( line.hasOption( "namespace" ) ) {
//                // print the value of block-size
//                System.out.println( line.getOptionValue( "namespace" ) );
//            }
        }
        catch( ParseException exp ) {
            // oops, something went wrong
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }
        // load Jaft configuration
        if( line.hasOption( "config" ) ) {
            JaftContext.loadConfiguration(line.getOptionValue( "config" ));
        }
        else
        {
            JaftContext.loadConfiguration("./src/main/resources/config.json");
        }

        // start server
        if(subCommand.equals("start"))
        {
            if(line.hasOption( "server" )) {
                logger.info("starting jaft server on port=" + Integer.toString(JaftContext.getLocalPort()));
                final JaftNettyServer jaftServer = new JaftNettyServer(JaftContext.getLocalPort());
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            jaftServer.run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
                JaftNettyServer.startClients(JaftContext.getLocalPort());
                electionTimer.start();
                t.join();
            }
        }

        //set key value
        if(subCommand.equals("set") || subCommand.equals("get"))
        {
            String namespace = "default";
            String key = "key";
            String value = "";
            if( line.hasOption( "namespace" ) ) {
                namespace = line.getOptionValue( "namespace" );
            }
            if( line.hasOption("key") ){
                key = line.getOptionValue( "key" );
            }
            if( line.hasOption("value") ){
                value = line.getOptionValue( "value" );
            }

            KeyValueClient kvClient = new KeyValueClient("127.0.0.1", 9500);
            logger.info("handle key=" + key);
            if(subCommand.equals("set"))
            {
                System.out.println("Try to set key=" + key);
                kvClient.setKeyValue(namespace, key, value);
                System.out.println(String.format("Set key: %s=%s", key, value));

            }
            if(subCommand.equals("get"))
            {
                System.out.println("Try to get key=" + key);
                String retValue = kvClient.getValue(namespace, key);
                System.out.println(String.format("Get key: %s=%s", key, retValue));
            }
        }


    }

    public static ElectionTimer getElectionTimer() {
        return electionTimer;
    }

    public static HeartbeatTimer getHeartbeatTimer() {
        return heartbeatTimer;
    }
}
