package it.superamici.ircBot.plugins.quotemanager;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.listeners.IMessageListener;
import com.ircclouds.irc.api.state.IIRCState;
import it.superamici.ircBot.plugins.AbstractBotPlugin;

import java.io.IOException;

/**
 * Created by Stefano on 08/09/2014.
 */
public class QuoteManagerPlugin extends AbstractBotPlugin {
    private volatile IRCApi bot;
    private volatile Quote quotedb;
    private volatile IMessageListener quoteListener;

    @Override
    public String getName() {
        return "Quote Manager";
    }

    @Override
    public void onLoad(IRCApi ircApi) {
        this.bot = ircApi;
        this.quotedb = new Quote();
        this.quoteListener = new QuoteListener(bot, quotedb);
    }

    @Override
    public void onServerConnect(IIRCState ircState) {
        bot.addListener(quoteListener);
    }

}
