package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardSetSize extends BaseCommand {

    public LeaderboardSetSize() {
        setPermission("cvstats.leaderboards.setsize");
        setHelpValue("/cvstats leaderboards <leaderboard> setsize <size>", "Set the number of entries to show");
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

        if (index <= 0) {
            return sendError(sender, CommandErrors.GREATER_THAN_ZERO);
        }

        leaderboard.size = index;
        CVStats.getInstance().saveLeaderboardManager();
        leaderboard.reload();
        return sendSuccess(sender, "Set size of leaderboard " + leaderboard.id + " to be " + leaderboard.size + "!");
    }

}
