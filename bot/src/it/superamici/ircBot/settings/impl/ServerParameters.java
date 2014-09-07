package it.superamici.ircBot.settings.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ircclouds.irc.api.IServerParameters;
import com.ircclouds.irc.api.domain.IRCServer;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Basic implementation of the IServerParameters interface with an inner builder
 * Created by Stefano on 06/09/2014.
 */
public class ServerParameters implements IServerParameters {
    private final String nickname;
    private final List<String> alternativeNicknames;
    private final String ident;
    private final String realname;
    private final IRCServer server;

    ServerParameters(String nickname, List<String> alternativeNicknames, String ident, String realname, IRCServer server) {
        this.nickname = checkNotNull(nickname, "Missing nickname");
        checkArgument(!alternativeNicknames.isEmpty(), "Missing alternative nicknames");
        this.alternativeNicknames = ImmutableList.copyOf(alternativeNicknames);
        this.ident = checkNotNull(ident, "Missing ident");
        this.realname = checkNotNull(realname, "Missing real name");
        this.server = checkNotNull(server, "Missing IRC server");
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public List<String> getAlternativeNicknames() {
        return this.alternativeNicknames;
    }

    @Override
    public String getIdent() {
        return this.ident;
    }

    @Override
    public String getRealname() {
        return this.realname;
    }

    @Override
    public IRCServer getServer() {
        return this.server;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String nickname;
        private List<String> alternativeNicknames;
        private String ident;
        private String realName;
        private IRCServer server;

        public Builder() {
            this.alternativeNicknames = Lists.newArrayList();
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder addAlternativeNickname(String nickname) {
            this.alternativeNicknames.add(nickname);
            return this;
        }

        public Builder setIdent(String ident) {
            this.ident = ident;
            return this;
        }

        public Builder setRealname(String realName) {
            this.realName = realName;
            return this;
        }

        public Builder setServer(IRCServer server) {
            this.server = server;
            return this;
        }

        public ServerParameters build() {
            return new ServerParameters(nickname, alternativeNicknames, ident, realName, server);
        }
    }
}
