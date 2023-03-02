package org.cubeville.cvstats.commands;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;

import java.util.List;

public class AddLeaderboardCommand extends BaseCommand {

    public AddLeaderboardCommand(String permission) {
        super(permission);
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 1) return sendError(sender, CommandErrors.invalidParameterSize("/cvstats leaderboards create <leaderboard>"));
        String leaderboardName = args[0].toLowerCase();
        CVStats.getLeaderboards().createLeaderboard(leaderboardName);
        return sendSuccess(sender, "You created a new leaderboard with the name " + leaderboardName + "!");
    }
}
