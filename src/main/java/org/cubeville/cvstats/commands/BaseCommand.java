package org.cubeville.cvstats.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.*;
import java.util.List;

public abstract class BaseCommand {
    private String permission;
    private String commandShape;
    protected Map<String, BaseCommand> subCommands;

    public BaseCommand() {}

    public boolean runCommandIfPossible(CommandSender sender, String[] args) {
        return runCommandIfPossible(sender, args, List.of());
    }

    protected void setHelpValue(String command, String description) {
        commandShape = command;
        CommandList.commands.put(command, description);
    }

    protected void setPermission(String permission) {
        this.permission = permission;
    }

    protected boolean sendParamsError(CommandSender sender) {
        return sendError(sender, commandShape);
    }

    public boolean runCommandIfPossible(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage("You do not have permission to run this command!");
            return false;
        }
        if (subCommands != null && args.length > 0 && subCommands.containsKey(args[0].toLowerCase())) {
            return subCommands.get(args[0].toLowerCase()).runCommandIfPossible(sender, Arrays.copyOfRange(args, 1, args.length), passedArgs);
        }
        return runCommand(sender, args, passedArgs);
    }

    protected void addSubCommand(String commandName, BaseCommand command) {
        addSubCommand(List.of(commandName), command);
    }

    protected void addSubCommand(List<String> commandNames, BaseCommand command) {
        if (subCommands == null) {
            subCommands = new HashMap<>();
        }

        for (String commandName : commandNames) {
            subCommands.put(commandName.toLowerCase(), command);
        }
    }

    protected boolean sendError(CommandSender sender, String message) {
        TextComponent errorTc = new TextComponent("Error: ");
        errorTc.setBold(true);
        errorTc.setColor(ChatColor.of("#c20000"));

        TextComponent messageTc = new TextComponent(org.bukkit.ChatColor.translateAlternateColorCodes('&', message));
        messageTc.setColor(ChatColor.of("#ff6b6b"));
        errorTc.setBold(false);

        errorTc.addExtra(messageTc);
        sender.spigot().sendMessage(errorTc);
        return false;
    }

    protected boolean sendSuccess(CommandSender sender, String message) {
        sender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&a" + message));
        return true;
    }

    protected abstract boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs);
}
