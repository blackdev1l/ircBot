package it.superamici.ircBot.plugins;

import com.ircclouds.irc.api.Callback;
import com.ircclouds.irc.api.DCCManager;
import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.IServerParameters;
import com.ircclouds.irc.api.ctcp.DCCReceiveCallback;
import com.ircclouds.irc.api.ctcp.DCCSendCallback;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.filters.IMessageFilter;
import com.ircclouds.irc.api.listeners.IMessageListener;
import com.ircclouds.irc.api.state.IIRCState;

import java.io.File;
import java.net.SocketAddress;

/**
 * This class is used to control and limit the plugin accesses to the irc api
 * Created by Stefano on 08/09/2014.
 */
public class IRCApiPluginProxy implements IRCApi {
    private final IRCApi ircApi;

    public IRCApiPluginProxy(IRCApi ircApi) {
        this.ircApi = ircApi;
    }

    @Override
    public void connect(IServerParameters aServerParameters, Callback<IIRCState> aCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disconnect() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disconnect(String aQuitMessage) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void joinChannel(String aChannelName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void joinChannel(String aChannelName, Callback<IRCChannel> aCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void joinChannel(String aChannelName, String aKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void joinChannel(String aChannelName, String aKey, Callback<IRCChannel> aCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void leaveChannel(String aChannelName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void leaveChannel(String aChannelName, Callback<String> aCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void leaveChannel(String aChannelName, String aPartMessage) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void leaveChannel(String aChannelName, String aPartMessage, Callback<String> aCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeNick(String aNewNick) {
        ircApi.changeNick(aNewNick);
    }

    @Override
    public void changeNick(String aNewNick, Callback<String> aCallback) {
        ircApi.changeNick(aNewNick, aCallback);
    }

    @Override
    public void message(String aTarget, String aMessage) {
        ircApi.message(aTarget, aMessage);
    }

    @Override
    public void message(String aTarget, String aMessage, Callback<String> aCallback) {
        ircApi.message(aTarget, aMessage, aCallback);
    }

    @Override
    public void act(String aTarget, String aMessage) {
        ircApi.act(aTarget, aMessage);
    }

    @Override
    public void act(String aTarget, String aMessage, Callback<String> aCallback) {
        ircApi.act(aTarget, aMessage, aCallback);
    }

    @Override
    public void notice(String aTarget, String aMessage) {
        ircApi.notice(aTarget, aMessage);
    }

    @Override
    public void notice(String aTarget, String aMessage, Callback<String> aCallback) {
        ircApi.notice(aTarget, aMessage, aCallback);
    }

    @Override
    public void kick(String aChannel, String aNick) {
        ircApi.kick(aChannel, aNick);
    }

    @Override
    public void kick(String aChannel, String aNick, String aKickMessage) {
        ircApi.kick(aChannel, aNick, aKickMessage);
    }

    @Override
    public void kick(String aChannel, String aNick, Callback<String> aCallback) {
        ircApi.kick(aChannel, aNick, aCallback);
    }

    @Override
    public void kick(String aChannel, String aNick, String aKickMessage, Callback<String> aCallback) {
        ircApi.kick(aChannel, aNick, aKickMessage, aCallback);
    }

    @Override
    public void changeTopic(String aChannel, String aTopic) {
        ircApi.changeTopic(aChannel, aTopic);
    }

    @Override
    public void changeMode(String aModeString) {
        ircApi.changeMode(aModeString);
    }

    @Override
    public void rawMessage(String aMessage) {
        ircApi.rawMessage(aMessage);
    }

    @Override
    public void dccSend(String aNick, File aFile, DCCSendCallback aCallback) {
        ircApi.dccSend(aNick, aFile, aCallback);
    }

    @Override
    public void dccSend(String aNick, File aFile, Integer aTimeout, DCCSendCallback aCallback) {
        ircApi.dccSend(aNick, aFile, aTimeout, aCallback);
    }

    @Override
    public void dccSend(String aNick, Integer aListeningPort, File aFile, DCCSendCallback aCallback) {
        ircApi.dccSend(aNick, aListeningPort, aFile, aCallback);
    }

    @Override
    public void dccSend(String aNick, File aFile, Integer aListeningPort, Integer aTimeout, DCCSendCallback aCallback) {
        ircApi.dccSend(aNick, aFile, aListeningPort, aTimeout, aCallback);
    }

    @Override
    public void dccAccept(String aNick, File aFile, Integer aPort, Integer aResumePosition, DCCSendCallback aCallback) {
        ircApi.dccAccept(aNick, aFile, aPort, aResumePosition, aCallback);
    }

    @Override
    public void dccAccept(String aNick, File aFile, Integer aPort, Integer aResumePosition, Integer aTimeout, DCCSendCallback aCallback) {
        ircApi.dccAccept(aNick, aFile, aPort, aResumePosition, aTimeout, aCallback);
    }

    @Override
    public void dccReceive(File aFile, Integer aSize, SocketAddress aAddress, DCCReceiveCallback aCallback) {
        ircApi.dccReceive(aFile, aSize, aAddress, aCallback);
    }

    @Override
    public void dccResume(File aFile, Integer aResumePosition, Integer aSize, SocketAddress aAddress, DCCReceiveCallback aCallback) {
        ircApi.dccResume(aFile, aResumePosition, aSize, aAddress, aCallback);
    }

    @Override
    public DCCManager getDCCManager() {
        return ircApi.getDCCManager();
    }

    @Override
    public void addListener(IMessageListener aListener) {
        ircApi.addListener(aListener);
    }

    @Override
    public void deleteListener(IMessageListener aListener) {
        ircApi.deleteListener(aListener);
    }

    @Override
    public void setMessageFilter(IMessageFilter aFilter) {
        ircApi.setMessageFilter(aFilter);
    }
}
