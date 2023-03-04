package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardSetKey extends BaseCommand {

    public LeaderboardSetKey() {
        setPermission("cvstats.leaderboards.setkey");
        setHelpValue("/cvstats leaderboards <leaderboard> setkey <key>", "Set the key to sort a leaderboard on");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        leaderboard.key = args[0].toLowerCase();
        CVStats.getInstance().saveLeaderboardManager();
        leaderboard.reload();
        return sendSuccess(sender, "Set key of leaderboard " + leaderboard.id + " to be " + leaderboard.key + "!");
    }

}
