package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public class LeaderboardDisplaysGroup extends BaseCommand {

    public LeaderboardDisplaysGroup() {
        addSubCommand(List.of("add", "create"), new LeaderboardDisplaysAdd());
        addSubCommand(List.of("remove", "delete"), new LeaderboardDisplaysRemove());
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        return sendError(sender, CommandErrors.DEFAULT_ERROR);
    }

}
