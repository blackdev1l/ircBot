package it.superamici.ircBot.plugins.linkparser;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.ircclouds.irc.api.listeners.VariousMessageListenerAdapter;
import org.jsoup.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cristian on 9/1/2014.

 TODO: Refactoring
 TODO: General link parser

 */

public class LinkParser extends VariousMessageListenerAdapter {
    private IRCApi bot;
    public LinkParser(IRCApi bot) {
        this.bot = bot;
    }

    @Override
    public void onChannelMessage(ChannelPrivMsg aMsg) {
        ArrayList<CharSequence> list = retrieveLinks(aMsg.getText());
        for (CharSequence s : list) {
            Document doc = null;
            try {
                doc = Jsoup.connect(s.toString()).get();
                assert doc != null;
                String title = doc.title();
                bot.message(aMsg.getChannelName(), title);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    /**
     * Thank you http://stackoverflow.com/questions/6384240/how-to-parse-a-url-from-a-string-in-android
     * @param text
     * @return an arraylist with all links found in a message
     */
    private ArrayList retrieveLinks(String text) {
        ArrayList links = new ArrayList();

        String regex = "\\(?\\b(http|s://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while(m.find()) {
            String urlStr = m.group();
            char[] stringArray1 = urlStr.toCharArray();

            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {

                char[] stringArray = urlStr.toCharArray();

                char[] newArray = new char[stringArray.length-2];
                System.arraycopy(stringArray, 1, newArray, 0, stringArray.length-2);
                urlStr = new String(newArray);
                System.out.println("Finally Url ="+newArray.toString());

            }
            System.out.println("...Url..."+urlStr);
            links.add(urlStr);
        }
        return links;
    }

}
