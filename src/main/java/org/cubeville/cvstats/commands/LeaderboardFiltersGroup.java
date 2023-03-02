package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public class LeaderboardFiltersGroup extends BaseCommand {

    public LeaderboardFiltersGroup() {
        addSubCommand(List.of("add", "create"), new LeaderboardFiltersAdd());
        addSubCommand(List.of("remove", "delete"), new LeaderboardFiltersRemove());
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        return sendError(sender, CommandErrors.DEFAULT_ERROR);
    }

}
