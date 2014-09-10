package it.superamici.ircBot.plugins.linkparser;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.listeners.IMessageListener;
import com.ircclouds.irc.api.state.IIRCState;
import it.superamici.ircBot.plugins.AbstractBotPlugin;

/**
 * Created by Stefano on 08/09/2014.
 */
public class LinkParserPlugin extends AbstractBotPlugin {
    private volatile IRCApi bot;
    private volatile IMessageListener linkListener;

    @Override
    public String getName() {
        return "Link Parser";
    }

    @Override
    public void onLoad(IRCApi ircApi) {
        this.bot = ircApi;
        this.linkListener = new LinkParser(bot);
    }

    @Override
    public void onServerConnect(IIRCState ircState) {
        bot.addListener(linkListener);
    }

}
