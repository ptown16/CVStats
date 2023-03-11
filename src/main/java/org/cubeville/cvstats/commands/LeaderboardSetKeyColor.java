package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardSetKeyColor extends BaseCommand {

    public LeaderboardSetKeyColor() {
        setPermission("cvstats.leaderboards.setkeycolor");
        setHelpValue("/cvstats leaderboards <leaderboard> setkeycolor <#ffffff>", "Set the key to sort a leaderboard on");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        String color = args[0].toLowerCase();
        if (!CVStats.HEX_PATTERN.matcher(color).matches()) return sendError(sender, CommandErrors.invalidColor(color));
        leaderboard.keyColor = color;
        CVStats.getInstance().saveLeaderboardManager();
        leaderboard.reload();
        return sendSuccess(sender, "Set key color of leaderboard " + leaderboard.id + " to be " + leaderboard.key + "!");
    }

}
