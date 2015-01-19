package it.superamici.ircBot;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.domain.messages.ChannelActionMsg;
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
        /*
        N3S SEI UN COGLIONE

        else if(aMsg.getText().equals("!leave")) {
            bot.message(aMsg.getChannelName(),"leaving");
            bot.leaveChannel(aMsg.getChannelName());
        }
        */
        else if(aMsg.getText().equals("!source")) {
            bot.message(aMsg.getChannelName(),"https://github.com/blackdev1l/ircBot");
        }
        else if(aMsg.getText().equals("!stats")) {
            bot.message(aMsg.getChannelName(),"http://superamici.tk/");
        }
        else if(aMsg.getText().contains("Ciao a tutti")) {
            bot.message(aMsg.getChannelName(),"Ciao "+aMsg.getSource().getNick());
        }
        else if(aMsg.getText().contains("Salve a tutti")) {
            bot.message(aMsg.getChannelName(),"Salve "+aMsg.getSource().getNick());
        }
        else if(aMsg.getText().contains("brava Silvia")) {
            bot.act(aMsg.getChannelName(),"arrossisce");
        }
        else if(aMsg.getText().contains("buonanotte") ||
                aMsg.getText().contains("buona notte")) {
            bot.message(aMsg.getChannelName(),"Buonanotte "+aMsg.getSource().getNick());
        }
    }

    @Override
    public void onChannelAction(ChannelActionMsg aMsg) {
        if(aMsg.getText().contains("tip")) {
            bot.act(aMsg.getChannelName(),"tips fedora");
            if(aMsg.getSource().getNick().equals("Amico_i")) {
                bot.message(aMsg.getChannelName(),"Sono Euforica.");
            }
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
