package it.superamici.ircBot.settings.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.ircclouds.irc.api.IServerParameters;
import it.superamici.ircBot.settings.IBotSettings;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Stefano on 06/09/2014.
 */
public class BotSettings implements IBotSettings {

    private final IServerParameters serverParameters;
    private final Set<String> channelsToJoin;

    BotSettings(IServerParameters serverParameters, Set<String> channelsToJoin) {
        this.serverParameters = checkNotNull(serverParameters, "Missing Server Parameters");
        checkArgument(!channelsToJoin.isEmpty(), "No channels to join");
        this.channelsToJoin = ImmutableSet.copyOf(channelsToJoin);
    }

    @Override
    public IServerParameters getServerParameters() {
        return this.serverParameters;
    }

    @Override
    public Set<String> getChannelsToJoin() {
        return this.channelsToJoin;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ServerParameters serverParameters;
        private final Set<String> channelsToJoin;

        public Builder() {
            channelsToJoin = Sets.newHashSet();
        }

        public Builder setServerParameters(ServerParameters serverParameters) {
            this.serverParameters = serverParameters;
            return this;
        }

        public Builder addChannelToJoin(String channel) {
            channelsToJoin.add(channel);
            return this;
        }

        public BotSettings build() {
            return new BotSettings(serverParameters, channelsToJoin);
        }
    }
}
