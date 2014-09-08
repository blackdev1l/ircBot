package it.superamici.ircBot.plugins;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.state.IIRCState;
import it.superamici.ircBot.plugins.IBotPlugin;

/**
 * Created by Stefano on 08/09/2014.
 */
public abstract class AbstractBotPlugin implements IBotPlugin {

    @Override
    public void onLoad(IRCApi ircApi) {

    }

    @Override
    public void onServerConnect(IIRCState ircState) {

    }

    @Override
    public void onChannelJoin(IRCChannel channel) {

    }

    @Override
    public void onChannelLeave(IRCChannel channel) {

    }
}
