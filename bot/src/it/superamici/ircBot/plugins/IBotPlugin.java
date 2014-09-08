package it.superamici.ircBot.plugins;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.IServerParameters;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.state.IIRCState;

/**
 * Interface that every plugin must implement.
 * Created by Stefano on 08/09/2014.
 */
public interface IBotPlugin {

    /**
     * Returns the plugin name
     * @return
     */
    String getName();

    /**
     * Method called when the plugin is loaded at startup
     * @param ircApi the irc api object
     */
    void onLoad(IRCApi ircApi);

    /**
     * Method called when the bot connects to the irc server
     * @param ircState object used to retrieve the irc state information
     */
    void onServerConnect(IIRCState ircState);

    /**
     * Method called when the bot joins a channel
     * @param channel
     */
    void onChannelJoin(IRCChannel channel);

    /**
     * Method called then the bot leaves a channel
     * @param channel
     */
    void onChannelLeave(IRCChannel channel);
}
