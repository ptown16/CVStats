package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;
import org.cubeville.cvstats.leaderboards.LeaderboardSortBy;

import java.util.List;

public class LeaderboardSetSortBy extends BaseCommand {

    public LeaderboardSetSortBy() {
        setPermission("cvstats.leaderboards.sortby");
        setHelpValue("/cvstats leaderboards <leaderboard> sortby <count|ascending|descending>", "Set organization method for leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        try {
            leaderboard.sortBy = LeaderboardSortBy.valueOf(args[0].toUpperCase());
            CVStats.getInstance().saveLeaderboardManager();
        } catch (IllegalArgumentException e) {
            return sendError(sender, CommandErrors.invalidSortValue(args[0]));
        }
        return sendSuccess(sender, "Set sort method of leaderboard " + leaderboard.id + " to be " + leaderboard.sortBy + "!");
    }

}
