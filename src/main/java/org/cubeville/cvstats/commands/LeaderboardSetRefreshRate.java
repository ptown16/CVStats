package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardSetRefreshRate extends BaseCommand {

    public LeaderboardSetRefreshRate() {
        setPermission("cvstats.leaderboards.setrefreshrate");
        setHelpValue("/cvstats leaderboards <leaderboard> setrefreshrate <seconds>", "Set the refresh rate of the leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        int index;
        try {
            index = Integer.parseInt(args[0]);
        } catch(NumberFormatException e) {
            return sendError(sender, CommandErrors.invalidIntegerValue(args[0]));
        }

        if (index < 0) {
            return sendError(sender, CommandErrors.POSITIVE_NUMBER);
        }

        leaderboard.refreshRate = index;
        CVStats.getInstance().saveLeaderboardManager();
        leaderboard.reload();
        if (index == 0) {
            return sendSuccess(sender, "Leaderboard " + leaderboard.id + " will now refresh only when manually reloaded.");
        }
        return sendSuccess(sender, "Leaderboard " + leaderboard.id + " will now refresh every " + leaderboard.refreshRate + " seconds.");
    }

}
