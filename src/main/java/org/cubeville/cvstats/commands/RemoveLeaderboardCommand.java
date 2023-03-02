package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.LeaderboardManager;

import java.util.List;

public class RemoveLeaderboardCommand extends BaseCommand {

    public RemoveLeaderboardCommand(String permission) {
        super(permission);
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendError(sender, CommandErrors.invalidParameterSize("/cvstats leaderboards delete <leaderboard>"));
        String leaderboardName = args[0].toLowerCase();
        LeaderboardManager leaderboards = CVStats.getLeaderboards();
        // check if the leaderboard we are deleting exists
        if (!leaderboards.hasLeaderboard(leaderboardName)) return sendError(sender, CommandErrors.leaderboardDoesntExist(leaderboardName));
        CVStats.getLeaderboards().deleteLeaderboard(leaderboardName);
        return sendSuccess(sender, "You deleted the leaderboard named " + leaderboardName + ".");
    }
}
