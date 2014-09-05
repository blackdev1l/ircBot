package com.superamici.ircBot;
/**
 * Created by Cristian on 8/29/2014.
 */

import com.ircclouds.irc.api.*;
import com.ircclouds.irc.api.domain.*;
import com.ircclouds.irc.api.state.*;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        IRCApi bot = new IRCApiImpl(true);
        Quote quote = new Quote();

        bot.connect(getServerParams("nick", Arrays.asList("BalotelliReturns", "Balobot"), "openbotter", "ident", "irc.eu.synirc.net", true), new Callback<IIRCState>() {
            @Override
            public void onSuccess(IIRCState iircState) {
                bot.joinChannel("#test1", new Callback<IRCChannel>() {
                    @Override
                    public void onSuccess(IRCChannel ircChannel) {
                        MessageListener listener_default = new MessageListener(bot,ircChannel);
                        YoutubeLink listener_link = new YoutubeLink(bot,ircChannel);
                        QuoteListener listener_quote = new QuoteListener(bot,ircChannel,quote);
                        bot.addListener(listener_default);
                        bot.addListener(listener_link);
                        bot.addListener(listener_quote);
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }

    private static IServerParameters getServerParams(final String aNickname, final List<String> aAlternativeNicks,
                                                     final String aRealname, final String aIdent,
                                                     final String aServerName, final Boolean aIsSSLServer)
    {
        return new IServerParameters()
        {
            @Override
            public IRCServer getServer()
            {
                return new IRCServer(aServerName, aIsSSLServer);
            }

            @Override
            public String getRealname()
            {
                return aRealname;
            }

            @Override
            public String getNickname()
            {
                return aNickname;
            }

            @Override
            public String getIdent()
            {
                return aIdent;
            }

            @Override
            public List<String> getAlternativeNicknames()
            {
                return aAlternativeNicks;
            }
        };
    }

}
