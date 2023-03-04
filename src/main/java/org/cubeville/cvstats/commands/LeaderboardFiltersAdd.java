package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardFiltersAdd extends BaseCommand {

    public LeaderboardFiltersAdd() {
        setPermission("cvstats.leaderboards.filters.add");
        setHelpValue("/cvstats leaderboards <leaderboard> filters add <key:value>", "Add a filter to a leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        String[] kvPair = args[0].toLowerCase().split(":");
        if (kvPair.length != 2) return sendError(sender, CommandErrors.COLON_KEY_VALUE);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        leaderboard.addFilter(kvPair[0], kvPair[1]);
        CVStats.getInstance().saveLeaderboardManager();
        leaderboard.reload();
        return sendSuccess(sender, "Filtered leaderboard " + leaderboard.id + " so that " + kvPair[0] + " is always " + kvPair[1] + "!");
    }

}
