package it.superamici.ircBot.plugins.History;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.listeners.IMessageListener;
import com.ircclouds.irc.api.state.IIRCState;
import it.superamici.ircBot.plugins.AbstractBotPlugin;

/**
 * Created by Cristian on 11/25/2014.
 */
public class HistoryPlugin extends AbstractBotPlugin {

    private volatile IRCApi bot;
    private volatile IMessageListener HistoryListener;

    /**
     * Returns the plugin name
     *
     * @return
     */
    @Override
    public String getName() {
        return "History Plugin";
    }

    @Override
    public void onLoad(IRCApi ircApi) {
        this.bot = ircApi;
        this.HistoryListener = new History(bot);
    }

    @Override
    public void onServerConnect(IIRCState ircState) {
        bot.addListener(HistoryListener);
    }


}
