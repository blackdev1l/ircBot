package it.superamici.ircBot.plugins.quotemanager;

import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Cristian on 9/1/2014.
 * TODO: Complete the class
 * TODO: !findquote
 * TODO: !randomquote
 */
public class Quote {
    private MongoClient mongoClient;
    private DB db;
    private DBCollection coll;

    public Quote() {
        mongoClient = null;
        try {
            MongoCredential credential = MongoCredential.createMongoCRCredential("cristian", "testing", "portawai".toCharArray());
            mongoClient = new MongoClient(new ServerAddress("ds061189.mongolab.com",61189), Arrays.asList(credential));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        DB db = mongoClient.getDB("testing");
        coll = db.getCollection("Quote");

    }

    /**
     * TODO: Assert
     */

    public long add(ChannelPrivMsg aMsg) {
        BasicDBObject obj = new BasicDBObject("id",coll.getCount());
        obj.put("name",aMsg.getSource().getNick());
        obj.put("msg", aMsg.getText().substring(10));
        coll.insert(obj);
        return coll.getCount()-1;
    }

    /**
     *
     * @param id
     * @return
     */
    public String showByID(int id) {
        BasicDBObject query = new BasicDBObject();
        query.put("id",id);
        DBCursor cursor = coll.find(query);
        return parseQuery(cursor);
    }


    public void search(ChannelPrivMsg aMsg,IRCApi bot) throws InterruptedException {
        String search = aMsg.getText().substring(11);
        String searchRegEx = ".*" + search + ".*";
        Pattern pattern = Pattern.compile(searchRegEx, Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject("msg", pattern);
        DBCursor cursor = coll.find(query);
        while(cursor.hasNext()) {
            bot.message(aMsg.getChannelName(),parseQuery(cursor));
            System.out.println(cursor.next());
            Thread.sleep(1000);
        }
    }

    public String random() {
        int lel;
        Random random = new Random();
        lel = random.nextInt((int) coll.getCount()); // scusa amico_i (scusa per cosa? Per aver chiamato una variabile lel? è_é)
        System.out.println(lel);
        return showByID(lel);
    }


    private String parseQuery(DBCursor cursor) {
        DBObject res = cursor.one();
        String name = res.get("name").toString();
        String msg = res.get("msg").toString();
        return name+": "+msg;
    }
}
