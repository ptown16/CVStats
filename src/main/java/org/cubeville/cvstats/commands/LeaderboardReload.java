package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;
import org.cubeville.cvstats.leaderboards.Leaderboard;
import org.cubeville.cvstats.leaderboards.LeaderboardManager;

import java.util.List;

public class LeaderboardReload extends BaseCommand {

    public LeaderboardReload() {
        setPermission("cvstats.leaderboards.reload");
        setHelpValue("/cvstats leaderboards <leaderboard> reload", "Reload a leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 0) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);
        leaderboard.reload();
        return sendSuccess(sender, "Reloaded leaderboard \"" + leaderboard.id + "\".");
    }
}
