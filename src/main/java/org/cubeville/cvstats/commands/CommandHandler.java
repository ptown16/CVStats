package org.cubeville.cvstats.commands;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.cubeville.cvstats.CVStats;

import java.util.*;

public class CommandHandler implements CommandExecutor, TabExecutor {

    private final Map<String, BaseCommand> commandMap = new HashMap<>() {{
        put("send", new SendMetricCommand("cvstats.send"));
        put("leaderboards", new LeaderboardCommand());
    }};

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase();
        if (!commandName.equals("cvstats") || args == null || args.length == 0) return false;
        BaseCommand baseCommand = commandMap.get(args[0].toLowerCase());
        if (baseCommand == null) {
            sender.sendMessage("§cThat command does not exist!");
            return true;
        }
        // remove first arg, since that's used to get the command
        baseCommand.runCommandIfPossible(sender, Arrays.copyOfRange(args, 1, args.length));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
