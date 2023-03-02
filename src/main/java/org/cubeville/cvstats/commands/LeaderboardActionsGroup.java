package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public class LeaderboardActionsGroup extends BaseCommand {

    public LeaderboardActionsGroup() {
        // edit / info? (similar to verify in CVGames, will show everything that can be tweaked)
        addSubCommand("setmetric", new LeaderboardSetMetric());
        addSubCommand("setkey", new LeaderboardSetKey());
        addSubCommand("sortby", new LeaderboardSetSortBy());
        addSubCommand("displays", new LeaderboardDisplaysGroup());
        addSubCommand("filters", new LeaderboardFiltersGroup());
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        return sendError(sender, CommandErrors.DEFAULT_ERROR);
    }
}
