package it.superamici.ircBot.plugins.quotemanager;

import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
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
        return parseQuery(cursor.next());
    }


    public ArrayList<String> search(String search)  {
        ArrayList<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject("msg", pattern);
        DBCursor cursor = coll.find(query);
        while(cursor.hasNext()) {
            list.add(parseQuery(cursor.next()));
            System.out.println(cursor);
        }
        return list;
    }

    public String random() {
        int lel;
        Random random = new Random();
        lel = random.nextInt((int) coll.getCount()); // scusa amico_i (scusa per cosa? Per aver chiamato una variabile lel? è_é)
        System.out.println(lel);
        return showByID(lel);
    }


    private String parseQuery(DBObject cursor) {
        //String name = cursor.get("name").toString();
        String msg = cursor.get("msg").toString();
        //return name+": "+msg;
        return msg;
    }
}
