package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardSetTitle extends BaseCommand {

    public LeaderboardSetTitle() {
        setPermission("cvstats.leaderboards.settitle");
        setHelpValue("/cvstats leaderboards <leaderboard> settitle <title>", "Set the title of the leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length < 1) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        String title = String.join(" ", args);
        // if in quotes, get the stuff in the quotes
        if (title.charAt(0) == '"' && title.charAt(title.length() - 1) == '"') {
            title = title.substring(1, title.length() - 1);
        }
        leaderboard.setTitle(title);
        CVStats.getInstance().saveLeaderboardManager();
        leaderboard.reload();
        return sendSuccess(sender, "&aSet title of leaderboard " + leaderboard.id + " to be \"" + leaderboard.getTitleString() + "&a\" !");
    }

}
