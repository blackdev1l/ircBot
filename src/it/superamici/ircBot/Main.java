package it.superamici.ircBot;
/**
 * Created by Cristian on 8/29/2014.
 */

import com.ircclouds.irc.api.Callback;
import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.IRCApiImpl;
import com.ircclouds.irc.api.IServerParameters;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.domain.IRCServer;
import com.ircclouds.irc.api.state.IIRCState;
import it.superamici.ircBot.settings.IBotSettings;
import it.superamici.ircBot.settings.ISettingsFileParser;
import it.superamici.ircBot.settings.SettingsFileParserException;
import it.superamici.ircBot.settings.impl.XmlSettingsFileParser;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ISettingsFileParser fileParser = new XmlSettingsFileParser();

        try {
            IBotSettings botSettings = fileParser.parseFile(new File("settings.xml"));

            IRCApi bot = new IRCApiImpl(true);
            Quote quote = new Quote();

            bot.connect(botSettings.getServerParameters(), new Callback<IIRCState>() {
                @Override
                public void onSuccess(IIRCState iircState) {

                    MessageListener listener_default = new MessageListener(bot);
                    YoutubeLink listener_link = new YoutubeLink(bot);
                    QuoteListener listener_quote = new QuoteListener(bot,quote);
                    bot.addListener(listener_default);
                    bot.addListener(listener_link);
                    bot.addListener(listener_quote);

                    for (String channelName : botSettings.getChannelsToJoin()) {
                        bot.joinChannel(channelName);
                    }

                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (SettingsFileParserException e) {
            e.printStackTrace();
        }

    }



}
