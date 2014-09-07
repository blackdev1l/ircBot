package it.superamici.ircBot.settings;

import com.ircclouds.irc.api.IServerParameters;
import com.ircclouds.irc.api.domain.IRCChannel;

import java.util.Set;

/**
 * Interface that stores the bot settings
 * Created by Stefano on 06/09/2014.
 */
public interface IBotSettings {

    /**
     * Returns the parameters used to connect to an irc server
     * @return
     */
    IServerParameters getServerParameters();

    /**
     * Returns the list of channels to join on start-up
     * @return
     */
    Set<String> getChannelsToJoin();
}
