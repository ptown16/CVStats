package org.cubeville.cvstats.commands;

import org.apache.commons.lang.StringUtils;
import org.cubeville.cvstats.leaderboards.LeaderboardSortBy;

public class CommandErrors {

    public static final String DEFAULT_ERROR = "Invalid command, Enter \"/cvstats help\" to view all commands for this plugin.";
    public static final String NO_CONSOLE_SEND = "You cannot send this command from console!";
    public static final String COLON_KEY_VALUE = "There must be exactly 1 colon when defining a key value pair";

    public static String invalidParameterSize(String correctShape) {
        return "Invalid parameter size. Did you mean \"" + correctShape + "\" ?";
    }

    public static String leaderboardDoesntExist(String leaderboardName) {
        return "Leaderboard with name \"" + leaderboardName + "\" does not exist!";
    }

    public static String invalidSortValue(String sortValue) {
        String properValues = StringUtils.join(LeaderboardSortBy.values(), ", ");
        return sortValue + " is not a valid sorting method! Correct values are: &f" + properValues;
    }

    public static String invalidIntegerValue(String index) {
        return index + " is not a valid integer value!";
    }

    public static String integerOutOfBounds(int index) {
        return "There is nothing at index " + index + ".";
    }

    public static String keyDoesNotExist(String leaderboardId, String key) {
        return "There is no filter with key " + key + " in leaderboard " + leaderboardId + ".";
    }
}
