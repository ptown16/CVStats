package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardSetMetric extends BaseCommand {

    public LeaderboardSetMetric(String permission) {
        super(permission);
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendError(sender, CommandErrors.invalidParameterSize("/cvstats leaderboards <leaderboard> setmetric <metric>"));
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        leaderboard.metric = args[0].toLowerCase();
        CVStats.getInstance().saveLeaderboardManager();
        return sendSuccess(sender, "Set metric of leaderboard " + leaderboard.id + " to be " + leaderboard.metric + "!");
    }

}
