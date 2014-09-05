package it.superamici.ircBot;

import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by Cristian on 9/1/2014.
 * TODO: Complete the class
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

    //TODO: Assert
    public void add(ChannelPrivMsg aMsg) {
        BasicDBObject obj = new BasicDBObject("id",coll.getCount())
                .append("name",aMsg.getSource().getNick())
                .append("msg",aMsg.getText().substring(5));
        coll.insert(obj);
    }

    //TODO: Assert
    public String showByID(String id) {
        BasicDBObject obj = new BasicDBObject("id",id);
        DBCursor cursor = coll.find(obj);
        DBObject res = cursor.getKeysWanted();
        return res.toString();

    }

    //TODO: Assert
    public void remove(int id) {
        BasicDBObject obj = new BasicDBObject("id",id);
        coll.remove(obj);
    }
}
