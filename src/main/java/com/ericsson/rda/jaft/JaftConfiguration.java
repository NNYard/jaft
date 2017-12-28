package com.ericsson.rda.jaft;

import java.util.List;

public class JaftConfiguration {
    private String local_addr;
    private int local_port;
    private List<String> servers;

    public String getLocal_addr() {
        return local_addr;
    }
    public void setLocal_addr(String local_addr) {
        this.local_addr = local_addr;
    }

    public int getLocal_port() {
        return local_port;
    }
    public void setLocal_port(int local_port) {
        this.local_port = local_port;
    }

    public List<String> getServers()
    {
        return servers;
    }
    public void setServers(List<String> servers)
    {
        this.servers = servers;
    }
}
