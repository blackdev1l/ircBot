package it.superamici.ircBot.plugins.History;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.domain.messages.ChanJoinMessage;
import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.ircclouds.irc.api.listeners.VariousMessageListenerAdapter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Cristian on 11/25/2014.
 */
public class History extends VariousMessageListenerAdapter {
    private IRCApi bot;
    public History(IRCApi bot) {
        this.bot = bot;
    }

    @Override
    public void onChannelMessage(ChannelPrivMsg aMsg) {
        Date dnow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(aMsg.getChannelName()+".log", true)))) {
            out.println("("+ft.format(dnow)+") "+aMsg.getSource().getNick()+":"+" "+aMsg.getText());
            out.close();
        }catch (IOException e) {

        }

        if(aMsg.getText().contains("!tail")) {
            String tmp  = aMsg.getText().substring(6).trim();

            if(tmp.contains("o")) {
                int time = Integer.parseInt(tmp.substring(0,tmp.length()-1));

                Calendar cal = Calendar.getInstance();
                cal.setTime(dnow);
                cal.add(Calendar.HOUR, -time);
                Date dback = cal.getTime();
                SimpleDateFormat newFt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                reverseReader(aMsg.getChannelName(),time);
            }
            else {
                int time = Integer.parseInt(tmp);
                List out = reverseReader(aMsg.getChannelName()+".log",time);
                try {
                    String url = sendToPastebin(out);
                    bot.message(aMsg.getChannelName(),url);
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List reverseReader(String channelName, int time) {
        RandomAccessFile stream = null;
        List out = new ArrayList<String>();
        try {
            stream = new RandomAccessFile(channelName, "r");
            long pos = stream.length();
            safeSeek(stream, --pos);
            int c;
            int nLines = 0;
            while (pos >= 0 && nLines < time + 1 && (c = stream.read()) != -1) {
                if (c == '\n') {
                    nLines++;
                }
                safeSeek(stream, --pos);
            }
            stream.seek(pos+2);
            int i=0;
            String line;
            while ((line = stream.readLine()) != null) {
                System.out.println(line);
                out.add(line+'\n');
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    private static void safeSeek(RandomAccessFile stream, long pos)
            throws IOException {
        if (pos > 0) {
            stream.seek(pos);
        } else {
            stream.seek(0);
        }
    }

    private String sendToPastebin(List msg) throws UnirestException {
        String url = "http://pastebin.com/api/api_post.php";
        HttpResponse<String> response = Unirest.post(url)
                .header("accept", "application/string")
                .field("api_dev_key", "645ea11231f171b924231c88475648fc")
                .field("api_option","paste")
                .field("api_paste_code", msg)
                .asString();
        return response.getBody();

    }
}
