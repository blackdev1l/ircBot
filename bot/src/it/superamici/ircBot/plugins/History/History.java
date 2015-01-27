package it.superamici.ircBot.plugins.History;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.ircclouds.irc.api.listeners.VariousMessageListenerAdapter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Cristian on 11/25/2014.
 */
public class History extends VariousMessageListenerAdapter {
    private IRCApi bot;
    public History(IRCApi bot) {
        this.bot = bot;
    }

    /*
        Every message is saved on hard disk
        file's name is chanName.log
     */
    @Override
    public void onChannelMessage(ChannelPrivMsg aMsg) {
        Date dnow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("[HH:mm:ss]");

        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(aMsg.getChannelName()+".log", true)))) {
            out.println(ft.format(dnow)+" <"+aMsg.getSource().getNick()+"> "+aMsg.getText());
            out.close();
        }catch (IOException e) {

        }

        if(aMsg.getText().contains("!tail")) {
            int time = getLines(aMsg.getText());

            List out = reverseReader(aMsg.getChannelName()+".log",time);
            if(aMsg.getText().contains("grep")) {
                out = grep(out,aMsg.getText());
            }
            try {
                String url = sendToPastebin(out);
                bot.message(aMsg.getChannelName(),url);
            } catch (UnirestException e) {
                e.printStackTrace();
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
                .field("api_paste_expire_date", "10M")
                .asString();
        return response.getBody();

    }

    /*
        Get in input the list already parsed from file and message
        It returns a new list which has only lines that contains "word"
    */
    private List grep(List list, String msg) {
        List found = new ArrayList<String>();
        /* i is a placeholder which points the start of the word to find */
        int i = msg.indexOf("grep");
        i+="grep ".length();
        String word = msg.substring(i);
        System.out.println("word is "+word);
        for(Object s : list) {
            if(s.toString().contains(msg)){
                System.out.println("found "+s.toString());
                found.add(s.toString());
            }
        }
        return found;
    }


    /**
     * Get the message and returns the number of lines to parse
     * @param msg   user's message
     * @return      line's number to parse from file
     */
    private int getLines(String msg) {
        char[] stringChar = msg.toCharArray();
        ArrayList<Character> ke = new ArrayList<Character>();
        StringBuffer result = new StringBuffer();
        for (char c : stringChar) {
            if(Character.isDigit(c)) result.append(c);
        }
        String str = result.toString();
        int time = Integer.parseInt(str);
        System.out.println("lines are "+time);
        return time;
    }
}
