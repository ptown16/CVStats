package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;

public abstract class BaseCommand {
    private final String permission;

    public BaseCommand(String permission) {
        this.permission = permission;
    }

    public boolean runCommandIfPossible(CommandSender sender, String[] args) {
        if (!sender.hasPermission(permission)) {
            sender.sendMessage("You do not have permission to run this command!");
            return false;
        }
        return runCommand(sender, args);
    }

    protected boolean sendError(CommandSender sender, String message) {
        sender.sendMessage("§cError: " + message);
        return false;
    }

    protected abstract boolean runCommand(CommandSender sender, String[] args);
}
