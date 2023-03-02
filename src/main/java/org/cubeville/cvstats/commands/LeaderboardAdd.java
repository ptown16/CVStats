package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;

import java.util.List;

public class LeaderboardAdd extends BaseCommand {

    public LeaderboardAdd() {
        setPermission("cvstats.leaderboards.add");
        setHelpValue("/cvstats leaderboards create <leaderboard>", "Create a leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        String leaderboardName = args[0].toLowerCase();
        CVStats.getLeaderboards().createLeaderboard(leaderboardName);
        return sendSuccess(sender, "You created a new leaderboard with the name " + leaderboardName + "!");
    }
}
