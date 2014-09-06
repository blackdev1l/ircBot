package it.superamici.ircBot.settings;

/**
 * Created by Stefano on 06/09/2014.
 */
public class SettingsFileParserException extends Exception {

    public SettingsFileParserException(String message) {
        super(message);
    }

    public SettingsFileParserException(Throwable cause) {
        super(cause);
    }

    public SettingsFileParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
