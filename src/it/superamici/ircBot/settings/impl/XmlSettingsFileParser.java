package it.superamici.ircBot.settings.impl;

import com.google.common.collect.ImmutableMap;
import it.superamici.ircBot.settings.IBotSettings;
import it.superamici.ircBot.settings.ISettingsFileParser;
import it.superamici.ircBot.settings.SettingsFileParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * XML based implementation of ISettingsFileParser
 * Created by Stefano on 06/09/2014.
 */
public class XmlSettingsFileParser implements ISettingsFileParser {

    private final Logger logger;

    private final Map<String, IElementCommand<BotSettings.Builder>> rootCommands;
    private final Map<String, IElementCommand<BotSettings.Builder>> channelsCommands;
    private final Map<String, IElementCommand<ServerParameters.Builder>> serverParamsCommands;
    private final Map<String, IElementCommand<ServerParameters.Builder>> altNicknamesCommands;
    private final Map<String, IElementCommand<IRCServerBuilder>> ircServerCommands;

    public XmlSettingsFileParser() {
        logger = LoggerFactory.getLogger(XmlSettingsFileParser.class);

        rootCommands = ImmutableMap.of(
                "serverParameters", new ServerParametersCommand(),
                "channels", new ChannelsCommand());

        channelsCommands = ImmutableMap.of(
                "channel", new ChannelCommand());

        serverParamsCommands = ImmutableMap.of(
                "nickname", new NicknameCommand(),
                "alternativeNicknames", new AltNicknamesCommand(),
                "ident", new IdentCommand(),
                "realname", new RealnameCommand(),
                "ircServer", new IrcServerCommand());

        altNicknamesCommands = ImmutableMap.of(
                "nickname", new AltNicknameCommand());

        ircServerCommands = ImmutableMap.of(
                "hostname", new HostnameCommand(),
                "isSSL", new IsSSLCommand(),
                "port", new PortCommand());
    }

    @Override
    public IBotSettings parseFile(File file) throws SettingsFileParserException {
        logger.info("Parsing settings file: {}", file.getAbsoluteFile());
        Document doc = getDocumentFromFile(file);

        BotSettings.Builder builder = BotSettings.builder();

        Element parent = doc.getDocumentElement();

        executeChildElementCommands(parent, builder, rootCommands);

        return builder.build();
    }


    /**
     * Analyzes the child elements of parent element and executes a command stored in the map for each recognized child
     * @param parent the parent node
     * @param context the context to be passed to the command
     * @param commandsToExecute maps tag names to commands to be executed
     * @param <T> the type of the context
     */
    private <T> void executeChildElementCommands(Element parent, T context, Map<String, IElementCommand<T>> commandsToExecute) {

        NodeList nodeList = parent.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                String tagName = ((Element) node).getTagName();
                IElementCommand<T> command = commandsToExecute.get(tagName);
                if (command != null) {
                    logger.debug("Processing tag {}", tagName);
                    command.execute((Element) node, context);
                } else {
                    logger.warn("Invalid tag {} on parent tag {}", tagName, parent.getTagName());
                }
            }
        }
    }

    private Document getDocumentFromFile(File file) throws SettingsFileParserException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (ParserConfigurationException | SAXException | IOException e) {
           throw new SettingsFileParserException(e);
        }
    }

    private interface IElementCommand<T> {
        void execute(Element element, T context);
    }

    private class ServerParametersCommand implements IElementCommand<BotSettings.Builder> {
        @Override
        public void execute(Element element, BotSettings.Builder settingsBuilder) {
            ServerParameters.Builder paramsBuilder = ServerParameters.builder();
            executeChildElementCommands(element, paramsBuilder, serverParamsCommands);
            settingsBuilder.setServerParameters(paramsBuilder.build());
        }
    }

    private class ChannelCommand implements IElementCommand<BotSettings.Builder> {
        @Override
        public void execute(Element element, BotSettings.Builder settingsBuilder) {
            String channelName = element.getFirstChild().getTextContent();
            logger.debug("Adding channel to join: {}", channelName);
            settingsBuilder.addChannelToJoin(channelName);
        }
    }

    private class ChannelsCommand implements IElementCommand<BotSettings.Builder> {
        @Override
        public void execute(Element element, BotSettings.Builder settingsBuilder) {
            executeChildElementCommands(element, settingsBuilder, channelsCommands);
        }
    }

    private class NicknameCommand implements IElementCommand<ServerParameters.Builder> {
        @Override
        public void execute(Element element, ServerParameters.Builder paramsBuilder) {
            String nickname = element.getFirstChild().getTextContent();
            logger.debug("Setting nickname: {}", nickname);
            paramsBuilder.setNickname(nickname);
        }
    }

    private class IdentCommand implements IElementCommand<ServerParameters.Builder> {
        @Override
        public void execute(Element element, ServerParameters.Builder paramsBuilder) {
            String ident = element.getFirstChild().getTextContent();
            logger.debug("Setting ident: {}", ident);
            paramsBuilder.setIdent(ident);
        }
    }

    private class RealnameCommand implements IElementCommand<ServerParameters.Builder> {
        @Override
        public void execute(Element element, ServerParameters.Builder paramsBuilder) {
            String realname = element.getFirstChild().getTextContent();
            logger.debug("Setting real name: {}", realname);
            paramsBuilder.setRealname(realname);
        }
    }

    private class AltNicknamesCommand implements IElementCommand<ServerParameters.Builder> {
        @Override
        public void execute(Element element, ServerParameters.Builder paramsBuilder) {
            executeChildElementCommands(element, paramsBuilder, altNicknamesCommands);
        }
    }

    private class AltNicknameCommand implements IElementCommand<ServerParameters.Builder> {
        @Override
        public void execute(Element element, ServerParameters.Builder paramsBuilder) {
            String nickname = element.getFirstChild().getTextContent();
            logger.debug("Adding nickname to alternative nicknames: {}", nickname);
            paramsBuilder.addAlternativeNickname(nickname);
        }
    }

    private class IrcServerCommand implements IElementCommand<ServerParameters.Builder> {
        @Override
        public void execute(Element element, ServerParameters.Builder paramsBuilder) {
            IRCServerBuilder serverBuilder = new IRCServerBuilder();
            executeChildElementCommands(element, serverBuilder, ircServerCommands);
            paramsBuilder.setServer(serverBuilder.build());
        }
    }

    private class HostnameCommand implements IElementCommand<IRCServerBuilder> {
        @Override
        public void execute(Element element, IRCServerBuilder serverBuilder) {
            String hostname = element.getFirstChild().getTextContent();
            logger.debug("Setting hostname: {}", hostname);
            serverBuilder.setHostname(hostname);
        }
    }

    private class IsSSLCommand implements IElementCommand<IRCServerBuilder> {
        @Override
        public void execute(Element element, IRCServerBuilder serverBuilder) {
            boolean isSSL = Boolean.parseBoolean(element.getFirstChild().getTextContent());
            logger.debug("Setting SSL: {}", isSSL);
            serverBuilder.setSSL(isSSL);
        }
    }

    private class PortCommand implements IElementCommand<IRCServerBuilder> {
        @Override
        public void execute(Element element, IRCServerBuilder serverBuilder) {
            int port = Integer.parseInt(element.getFirstChild().getTextContent());
            logger.debug("Setting port: {}", port);
            serverBuilder.setPort(port);
        }
    }
}
