package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public class LeaderboardActionsGroup extends BaseCommand {

    public LeaderboardActionsGroup() {
        // edit / info? (similar to verify in CVGames, will show everything that can be tweaked)
        addSubCommand("edit", new LeaderboardEdit());
        addSubCommand("setmetric", new LeaderboardSetMetric());
        addSubCommand("setkey", new LeaderboardSetKey());
        addSubCommand("sortby", new LeaderboardSetSortBy());
        addSubCommand("displays", new LeaderboardDisplaysGroup());
        addSubCommand("filters", new LeaderboardFiltersGroup());
        addSubCommand("setsize", new LeaderboardSetSize());
        addSubCommand("settitle", new LeaderboardSetTitle());
        addSubCommand("reload", new LeaderboardReload());
        addSubCommand("setvalue", new LeaderboardSetValue());
        addSubCommand("setrefreshrate", new LeaderboardSetRefreshRate());
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        return sendError(sender, CommandErrors.DEFAULT_ERROR);
    }
}
