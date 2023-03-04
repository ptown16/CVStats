package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.Arrays;
import java.util.List;

public class LeaderboardDisplaysAdd extends BaseCommand {

    public LeaderboardDisplaysAdd() {
        setPermission("cvstats.leaderboards.displays.add");
        setHelpValue("/cvstats leaderboards <leaderboard> displays add [title]", "Add a display to a leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (!(sender instanceof Player)) return sendError(sender, CommandErrors.NO_CONSOLE_SEND);
        String title = null;
        if (args.length != 0) {
            title = String.join(" ", args);
            // if in quotes, get the stuff in the quotes
            if (title.charAt(0) == '"' && title.charAt(title.length() - 1) == '"') {
                title = title.substring(1, title.length() - 2);
            }
        }
        Player player = (Player) sender;
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        leaderboard.addDisplay(player.getLocation());
        CVStats.getInstance().saveLeaderboardManager();
        leaderboard.reload();
        return sendSuccess(sender, "Set key of leaderboard " + leaderboard.id + " to be " + leaderboard.key + "!");
    }

}
