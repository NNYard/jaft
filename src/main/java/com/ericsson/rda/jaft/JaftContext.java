package com.ericsson.rda.jaft;

import com.ericsson.rda.jaft.storage.JaftLogStorage;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by exiango on 8/24/2017.
 */
public class JaftContext {

    private static final Logger logger = LoggerFactory.getLogger(Jaft.class);

    private static String localAddress;
    private static int localPort;
    private static List<String> peers;
    private static Map<String, JaftNettyClient> clients;
    private static JaftLogStorage jaftLogStorage;
    private static JaftNode node;

    public static String getLocalAddress() {
        return localAddress;
    }

    public static void setLocalAddress(String localAddress) {
        JaftContext.localAddress = localAddress;
    }

    public static int getLocalPort() {
        return localPort;
    }

    public static void setLocalPort(int localPort) {
        JaftContext.localPort = localPort;
    }

    public static List<String> getPeers() {
        if (peers == null) {
            peers = new ArrayList<String>();
        }
        return peers;
    }

    public static void setPeers(List<String> peers) {
        JaftContext.peers = peers;
    }

    public static void loadConfiguration(String config_file) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(config_file));
            JaftConfiguration conf = gson.fromJson(reader, JaftConfiguration.class);
            logger.info("local addr: " + conf.getLocal_addr());
            JaftContext.setLocalAddress(conf.getLocal_addr());
            logger.info("local port: " + Integer.toString(conf.getLocal_port()));
            JaftContext.setLocalPort(conf.getLocal_port());

            List<String> servers = conf.getServers();
            logger.info("sever list: " + Integer.toString(servers.size()));
            servers.remove(JaftContext.getLocalAddress());
            String server_str = "";
            for (String server : servers) {
                server_str = server_str + server + " ";
            }
            JaftContext.setPeers(servers);
            logger.info("sever list: " + Integer.toString(servers.size()));
            logger.info("peer list: " + server_str);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static Map<String, JaftNettyClient> getClients() {
        if (clients == null) {
            clients = new HashMap<String, JaftNettyClient>();
        }
        return clients;
    }

    public static void setClients(Map<String, JaftNettyClient> clients) {
        JaftContext.clients = clients;
    }

    public static JaftLogStorage getJaftLogStorage() {
        if (jaftLogStorage == null) {
            jaftLogStorage = new JaftLogStorage();
        }
        return jaftLogStorage;
    }

    public static void setJaftLogStorage(JaftLogStorage jaftLogStorage) {
        JaftContext.jaftLogStorage = jaftLogStorage;
    }

    public static JaftNode getNode() {
        try {
            if (node == null) {
                node = new JaftNode();
            }
        }
        catch (UnknownHostException ex)
        {
            logger.warn("Hostname can not be resolved");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return node;
    }

    public static void setNode(JaftNode node) {
        JaftContext.node = node;
    }
}
