package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardActionCommand extends BaseCommand {

    public LeaderboardActionCommand() {
        // edit / info? (similar to verify in CVGames, will show everything that can be tweaked)
        // setmetric -- which metric is being used?
        addSubCommand("setmetric", new LeaderboardSetMetric("cvstats.leaderboards.setmetric"));
        // setkey -- set the field for the left side key
        // sortby -- count by default, can sort ascending / descending for times or strings?
        // displays -- show all displays
        // display (will have add / delete subcmds)
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        return false;
    }
}
