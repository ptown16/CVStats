package org.cubeville.cvstats.commands;

public class CommandErrors {

    public static final String DEFAULT_ERROR = "Invalid command, Enter \"/cvstats help\" to view all commands for this plugin.";

    public static String invalidParameterSize(String correctShape) {
        return "Invalid parameter size. Did you mean \"" + correctShape + "\" ?";
    }

    public static String leaderboardDoesntExist(String leaderboardName) {
        return "Leaderboard with name \"" + leaderboardName + "\" does not exist!";
    }
}
