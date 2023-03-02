package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardFiltersRemove extends BaseCommand {

    public LeaderboardFiltersRemove() {
        setPermission("cvstats.leaderboards.filters.remove");
        setHelpValue("/cvstats leaderboards <leaderboard> filters remove <key>", "Remove a filter from a leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        String key = args[0].toLowerCase();
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        if (!leaderboard.hasFilter(key)) return sendError(sender, CommandErrors.keyDoesNotExist(leaderboard.id, key));
        leaderboard.removeFilter(key);
        CVStats.getInstance().saveLeaderboardManager();
        return sendSuccess(sender, "Removed filter " + args[0] + " from leaderboard " + leaderboard.id + "!");
    }

}
