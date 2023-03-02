package org.cubeville.cvstats.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends BaseCommand {

    HelpCommand() {
        setPermission("cvstats.help");
    }
    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        TextComponent result = new TextComponent("Commands for CVStats");
        result.setBold(true);
        result.setColor(ChatColor.of("#7ac142"));

        for (String command : CommandList.commands.keySet()) {
            result.addExtra("\n");
            // command
            TextComponent commandTc = new TextComponent(command);
            commandTc.setColor(ChatColor.of("#2d5980"));
            result.addExtra(commandTc);

            TextComponent separatorTc = new TextComponent(" - ");
            separatorTc.setColor(ChatColor.of("#7ac142"));
            result.addExtra(separatorTc);

            TextComponent descriptionTc = new TextComponent(CommandList.commands.get(command));
            descriptionTc.setColor(ChatColor.of("#b2b7bb"));
            result.addExtra(descriptionTc);
        }

        sender.spigot().sendMessage(result);
        return true;
    }
}
