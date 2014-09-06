package it.superamici.ircBot.settings;

import java.io.File;

/**
 * Interface used to parse a settings file to create a settings object
 * Different implementation of this class can be created to support different file formats (xml, properties, etc.)
 * Created by Stefano on 06/09/2014.
 */
public interface ISettingsFileParser {
    IBotSettings parseFile(File file) throws SettingsFileParserException;
}
