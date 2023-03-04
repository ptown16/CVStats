package org.cubeville.cvstats.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends BaseCommand {

    HelpCommand() {
        setPermission("cvstats.help");
    }
    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        TextComponent result = new TextComponent();

        TextComponent title = new TextComponent("Commands for CVStats");
        title.setBold(true);
        title.setColor(ChatColor.of("#5eff00"));
        result.addExtra(title);

        for (String command : CommandList.commands.keySet()) {
            result.addExtra("\n");
            // command
            TextComponent commandTc = new TextComponent(command);
            commandTc.setColor(ChatColor.of("#4fc7ff"));
            commandTc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(CommandList.commands.get(command))));
            commandTc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
            result.addExtra(commandTc);
        }

        sender.spigot().sendMessage(result);
        return true;
    }
}
