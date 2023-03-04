package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardSetMetric extends BaseCommand {

    public LeaderboardSetMetric() {
        setPermission("cvstats.leaderboards.setmetric");
        setHelpValue("/cvstats leaderboards <leaderboard> setmetric <metric>", "Set the metric to populate a leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        leaderboard.metric = args[0].toLowerCase();
        CVStats.getInstance().saveLeaderboardManager();
        leaderboard.reload();
        return sendSuccess(sender, "Set metric of leaderboard " + leaderboard.id + " to be " + leaderboard.metric + "!");
    }

}
