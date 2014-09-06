package it.superamici.ircBot.settings.impl;

import com.ircclouds.irc.api.domain.IRCServer;

/**
 * Builder class for IRCServer
 * TODO: implement password configuration
 * Created by Stefano on 06/09/2014.
 */
public class IRCServerBuilder {
    private static final int INVALID_PORT = -1;

    private String hostname;
    private int port = INVALID_PORT;
    private boolean isSSL = false;

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSSL(boolean isSSL) {
        this.isSSL = isSSL;
    }

    IRCServer build() {
        IRCServer builtServer;
        if (port == INVALID_PORT) {
            builtServer = new IRCServer(hostname, isSSL);
        } else {
            builtServer = new IRCServer(hostname, port, isSSL);
        }
        return  builtServer;
    }
}
