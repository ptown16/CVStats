package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.Arrays;
import java.util.List;

public class LeaderboardCommand extends BaseCommand {
    public LeaderboardCommand() {
        addSubCommand(List.of("add", "create"), new AddLeaderboardCommand("cvstats.leaderboards.add"));
        addSubCommand(List.of("remove", "delete"), new RemoveLeaderboardCommand("cvstats.leaderboards.remove"));
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length == 0) return sendError(sender, CommandErrors.DEFAULT_ERROR);
        Leaderboard leaderboard = CVStats.getLeaderboards().getLeaderboard(args[0].toLowerCase());
        if (leaderboard == null) return sendError(sender, CommandErrors.leaderboardDoesntExist(args[0]));
        LeaderboardActionCommand command = new LeaderboardActionCommand();
        return command.runCommandIfPossible(sender, Arrays.copyOfRange(args,1, args.length), List.of(leaderboard));
    }

}
