package it.superamici.ircBot;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.ircclouds.irc.api.domain.messages.ErrorMessage;
import com.ircclouds.irc.api.domain.messages.ServerPing;
import com.ircclouds.irc.api.listeners.VariousMessageListenerAdapter;
import java.lang.Object;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Cristian on 8/29/2014.
 */
public class MessageListener extends VariousMessageListenerAdapter {
    private IRCApi bot;
    public MessageListener(IRCApi bot) {
        this.bot = bot;
    }

    @Override
    public void onChannelMessage(ChannelPrivMsg aMsg) {

        if(aMsg.getText().equals("!ping"))
            bot.message(aMsg.getChannelName(), "pong!");
        else if(aMsg.getText().contains("!join #")) {
            String newChan = aMsg.getText().substring(6);
            bot.message(aMsg.getChannelName(),"Joining "+newChan);
            bot.joinChannel(newChan);
        }
        else if(aMsg.getText().contains(".j")) {
            bot.joinChannel("#superamici");
        }
        else if(aMsg.getText().equals("!leave")) {
            bot.message(aMsg.getChannelName(),"leaving");
            bot.leaveChannel(aMsg.getChannelName());
        }

        if(aMsg.getText().equals("!source")) {
            bot.message(aMsg.getChannelName(),"https://github.com/blackdev1l/ircBot");
        }
    }

    @Override
    public void onError(ErrorMessage aMsg) {
        System.out.println(aMsg.getText());
    }

    @Override
    public void onServerPing(ServerPing aMsg) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println("(PING) "+aMsg.getText()+" "+dateFormat.format(cal.getTime()));
    }
}
