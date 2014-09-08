package it.superamici.ircBot;
/**
 * Created by Cristian on 8/29/2014.
 */

import com.google.common.collect.Sets;
import com.ircclouds.irc.api.Callback;
import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.IRCApiImpl;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.state.IIRCState;
import it.superamici.ircBot.plugins.IBotPlugin;
import it.superamici.ircBot.plugins.IRCApiPluginProxy;
import it.superamici.ircBot.plugins.linkparser.LinkParserPlugin;
import it.superamici.ircBot.plugins.quotemanager.QuoteManagerPlugin;
import it.superamici.ircBot.settings.IBotSettings;
import it.superamici.ircBot.settings.ISettingsFileParser;
import it.superamici.ircBot.settings.SettingsFileParserException;
import it.superamici.ircBot.settings.impl.XmlSettingsFileParser;

import java.io.File;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        ISettingsFileParser fileParser = new XmlSettingsFileParser();

        // TODO: This should be read in the settings
        Set<IBotPlugin> plugins = Sets.newHashSet();
        plugins.add(new LinkParserPlugin());
        plugins.add(new QuoteManagerPlugin());

        try {
            IBotSettings botSettings = fileParser.parseFile(new File("settings.xml"));

            IRCApi bot = new IRCApiImpl(true);

            IRCApiPluginProxy botProxy = new IRCApiPluginProxy(bot);
            for (IBotPlugin plugin : plugins) {
                plugin.onLoad(botProxy);
            }

            bot.connect(botSettings.getServerParameters(), new Callback<IIRCState>() {
                @Override
                public void onSuccess(IIRCState iircState) {

                    MessageListener listener_default = new MessageListener(bot);
                    bot.addListener(listener_default);

                    for (IBotPlugin plugin : plugins) {
                        plugin.onServerConnect(iircState);
                    }

                    joinChannels(botSettings, bot, plugins);

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

    private static void joinChannels(IBotSettings botSettings, IRCApi bot, Set<IBotPlugin> plugins) {
        for (String channelName : botSettings.getChannelsToJoin()) {
            bot.joinChannel(channelName, new Callback<IRCChannel>() {
                @Override
                public void onSuccess(IRCChannel channel) {
                    for (IBotPlugin plugin : plugins) {
                        plugin.onChannelJoin(channel);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


}
