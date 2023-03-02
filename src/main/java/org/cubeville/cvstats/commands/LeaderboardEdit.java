package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardEdit extends BaseCommand {

    public LeaderboardEdit() {
        setPermission("cvstats.leaderboards.edit");
        setHelpValue("/cvstats leaderboards <leaderboard> edit", "Edit a leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        String[] kvPair = args[0].toLowerCase().split(":");
        if (kvPair.length != 2) return sendError(sender, CommandErrors.COLON_KEY_VALUE);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        leaderboard.addFilter(kvPair[0], kvPair[1]);
        CVStats.getInstance().saveLeaderboardManager();
        return sendSuccess(sender, "Filtered leaderboard " + leaderboard.id + " so that " + kvPair[0] + " is always " + kvPair[1] + "!");
    }

}
