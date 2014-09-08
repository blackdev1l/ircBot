package it.superamici.ircBot.plugins.linkparser;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.ircclouds.irc.api.listeners.VariousMessageListenerAdapter;
import org.jsoup.*;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Cristian on 9/1/2014.

 TODO: Refactoring
 TODO: General link parser

 */

public class YoutubeLink extends VariousMessageListenerAdapter {
    private IRCApi bot;
    public YoutubeLink(IRCApi bot) {
        this.bot = bot;
    }

    @Override
    public void onChannelMessage(ChannelPrivMsg aMsg) {
        if (aMsg.getText().contains("youtube.com/watch?")) {
            System.out.println("youtube link found");
            int set = aMsg.getText().indexOf("www");
            int offset = 0;
            if (aMsg.getText().contains("watch?feature=player_"))
                offset = set + 61;
            else
                offset = set + 35;
            String url = "http://" + aMsg.getText().substring(set, offset);
            try {
                Document doc = Jsoup.connect(url).get();
                String title = doc.title();
                bot.message(aMsg.getChannelName(), title);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (aMsg.getText().contains("youtu.be")) {
            System.out.println("youtube link found");
            int set = aMsg.getText().indexOf("you");
            int offset = set + 20;
            String url = "http://" + aMsg.getText().substring(set, offset);
            try {
                Document doc = Jsoup.connect(url).get();
                String title = doc.title();
                bot.message(aMsg.getChannelName(), title);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
