package it.superamici.ircBot.plugins.quotemanager;

import com.google.common.collect.Lists;
import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.ircclouds.irc.api.listeners.VariousMessageListenerAdapter;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

/**
 * Created by Cristian on 9/1/2014.
 * TODO: Complete the class
 */
public class QuoteListener extends VariousMessageListenerAdapter {
    private IRCApi bot;
    private Quote quote;

    public QuoteListener(IRCApi bot, Quote quote) {
        this.bot = bot;
        this.quote = quote;
    }

    @Override
    public void onChannelMessage(ChannelPrivMsg aMsg) {
        if (aMsg.getText().contains("!addquote") && aMsg.getText().length() >= 10) {
            long n = quote.add(aMsg);
            bot.message(aMsg.getChannelName(), "quote added [" + n + "]");

        } else if (aMsg.getText().contains("!quote")) {
            int n = Integer.parseInt(aMsg.getText().substring(7).trim());
            String msg = quote.showByID(n);
            bot.message(aMsg.getChannelName(), msg);
        } else if (aMsg.getText().contains("!randomquote")) {
            String msg = quote.random();
            bot.message(aMsg.getChannelName(), msg);

        } else if(aMsg.getText().contains("!findquote")) {
            findquote(aMsg);
        }
    }

    private void findquote(ChannelPrivMsg aMsg) {
        boolean query = false;
        String search = new String();
        if(aMsg.getText().contains("-q")) {
            query = true;
            search = aMsg.getText().substring(14);
        }
        else { search = aMsg.getText().substring(11); }
        ArrayList<String> list = quote.search(search);
        if(query) {
            getSearch(list,aMsg.getSource().getNick(),query);
        }
        else {
            getSearch(list,aMsg.getChannelName(),query);
        }
    }


    private void getSearch(ArrayList<String> list, String output,boolean query) {
        String ids = new String();
        int i = 1;
        if(!query) {
            for (String s : Lists.reverse(list)) {
                if (i != 6) {
                    bot.message(output, s);
                    i++;
                } else {
                    ids = ids.concat(s.substring(0, 5));
                    ids = ids.replace("<", " ");
                }
            }
            bot.message(output, ids);
        }
        else {
            for (String s : Lists.reverse(list)) {
                    bot.message(output, s);
            }
        }
    }
}