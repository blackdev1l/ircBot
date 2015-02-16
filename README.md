ircBot
======

Dummy bot written in java with the helps of [irc api](https://code.google.com/p/irc-api/) library

if you have a mongolab account (you should if you want to use this bot) , create in the top folder a mongo.txt file with inside 1 string per line, first line user, second line collection name, third line password db
 something like this: 
```
user
collectionName
password
```

compile with `mvn clean compile assembly:single` then copy your `settings.xml` && mongo.txt on target folder, execute with ` java -jar ircBot-1.0-SNAPSHOT-jar-with-dependencies.jar &`

###TODO
* Documentation
* Redesign
* ✓ Better Quote System
* ✓ Quoteview on website
