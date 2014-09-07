package it.superamici.ircBot;

import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by Cristian on 9/1/2014.
 * TODO: Complete the class
 * TODO: !searchquote
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

    /**
     * TODO: make it works
     * @param search
     * @return
     */
    public String search(String search) {
        BasicDBObject query = new BasicDBObject();
        query.put("$text",search);
        DBCursor cursor = coll.find(query);
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        return parseQuery(cursor);
    }



    private String parseQuery(DBCursor cursor) {
        DBObject res = cursor.one();
        String name = res.get("name").toString();
        String msg = res.get("msg").toString();
        return name+": "+msg;
    }
}
