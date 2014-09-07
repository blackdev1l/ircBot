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
        if(aMsg.getText().contains("!addquote") && aMsg.getText().length() >= 10) {
            long n = quote.add(aMsg);
            bot.message(aMsg.getChannelName(),"quote added ["+n+"]");

        }
        else if(aMsg.getText().contains("!quote")) {
            int n = Integer.parseInt(aMsg.getText().substring(7).trim());
            String msg = quote.showByID(n);
            bot.message(aMsg.getChannelName(),msg);
        }
        else if(aMsg.getText().contains("!findquote")) {
            String msg = quote.search(aMsg.getText().substring(11));
            bot.message(aMsg.getChannelName(),msg);
        }
        else if(aMsg.getText().contains("!randomquote")) {
            String msg = quote.random();
            bot.message(aMsg.getChannelName(),msg);

        }
    }
}
