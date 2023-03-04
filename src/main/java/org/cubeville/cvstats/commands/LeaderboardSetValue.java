package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardSetValue extends BaseCommand {

    public LeaderboardSetValue() {
        setPermission("cvstats.leaderboards.setvalue");
        setHelpValue("/cvstats leaderboards <leaderboard> setvalue <key>", "Set the value to sort a leaderboard on");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        leaderboard.value = args[0].toLowerCase();
        CVStats.getInstance().saveLeaderboardManager();
        leaderboard.reload();
        return sendSuccess(sender, "Set value of leaderboard " + leaderboard.id + " to be " + leaderboard.value + "!");
    }

}
