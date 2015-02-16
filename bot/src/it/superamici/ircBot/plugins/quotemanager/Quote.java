package it.superamici.ircBot.plugins.quotemanager;

import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.mongodb.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Cristian on 9/1/2014.
 */
public class Quote {
    private MongoClient mongoClient;
    private DB db;
    private DBCollection coll;
    private String user;
    private String password;
    private String dbName;

    public Quote()  {
        mongoClient = null;
        /*
            This is a placeholder code
            Put a file called "mongo.txt" in the root poject's folder with inside 3 strings
            user (user name of the db in mongolab)
            password (user password)
            collection name
         */
        List<String> records = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("mongo.txt"));
            String line;
            while (( line = reader.readLine()) != null)
            {
                records.add(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.user = records.get(0);
        this.password = records.get(1);
        this.dbName = records.get(2);

        try {
            MongoCredential credential = MongoCredential.createMongoCRCredential(user, dbName, password.toCharArray());
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
        String id = cursor.get("id").toString();
        String msg = cursor.get("msg").toString();
        //return name+": "+msg;
        return "["+id+"] "+msg;
    }
}
