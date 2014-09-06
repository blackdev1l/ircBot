package it.superamici.ircBot;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.ircclouds.irc.api.listeners.VariousMessageListenerAdapter;

/**
 * Created by Cristian on 9/1/2014.
 * TODO: Complete the class
 */
public class QuoteListener extends VariousMessageListenerAdapter {
    private IRCApi bot;
    private Quote quote;

    public QuoteListener(IRCApi bot,Quote quote) {
        this.bot = bot;
        this.quote = quote;
    }

    @Override
    public void onChannelMessage(ChannelPrivMsg aMsg) {
        if(aMsg.getText().contains("!add") && aMsg.getText().length() >= 5) {
            quote.add(aMsg);
            bot.message(aMsg.getChannelName(),"quote added");

        }
        else if(aMsg.getText().contains("!quote")) {
            String msg = quote.showByID(aMsg.getText().substring(7));
            bot.message(aMsg.getChannelName(),msg);
        }
    }
}
