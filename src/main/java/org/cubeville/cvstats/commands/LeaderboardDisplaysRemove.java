package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardDisplaysRemove extends BaseCommand {

    public LeaderboardDisplaysRemove() {
        setPermission("cvstats.leaderboards.displays.remove");
        setHelpValue("/cvstats leaderboards <leaderboard> displays remove <index>", "Remove a display from a leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        // check if index is valid
        int index;
        try {
            index = Integer.parseInt(args[0]);
        } catch(NumberFormatException e) {
            return sendError(sender, CommandErrors.invalidIntegerValue(args[0]));
        }

        // check if index is in bounds (we are going from 1 -> size)
        if (index > leaderboard.getDisplays().size() || index < 1) {
            return sendError(sender, CommandErrors.integerOutOfBounds(index));
        }

        leaderboard.removeDisplay(index - 1);
        CVStats.getInstance().saveLeaderboardManager();
        return sendSuccess(sender, "Removed display for leaderboard " + leaderboard.id + " at index " + index + "!");
    }

}
