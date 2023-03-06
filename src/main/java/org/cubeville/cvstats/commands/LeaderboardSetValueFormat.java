package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;
import org.cubeville.cvstats.leaderboards.LeaderboardSortBy;
import org.cubeville.cvstats.leaderboards.LeaderboardValueFormat;

import java.util.List;

public class LeaderboardSetValueFormat extends BaseCommand {

    public LeaderboardSetValueFormat() {
        setPermission("cvstats.leaderboards.valueformat");
        setHelpValue("/cvstats leaderboards <leaderboard> setvalueformat <default|time_milli|number>", "Set organization method for leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        try {
            leaderboard.valueFormat = LeaderboardValueFormat.valueOf(args[0].toUpperCase());
            CVStats.getInstance().saveLeaderboardManager();
            leaderboard.reload();
        } catch (IllegalArgumentException e) {
            return sendError(sender, CommandErrors.invalidValueFormat(args[0]));
        }
        return sendSuccess(sender, "Set value format of leaderboard " + leaderboard.id + " to be " + leaderboard.valueFormat + "!");
    }

}
