package org.cubeville.cvstats.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cubeville.cvstats.leaderboards.Leaderboard;

import java.util.List;

public class LeaderboardEdit extends BaseCommand {

    public LeaderboardEdit() {
        setPermission("cvstats.leaderboards.edit");
        setHelpValue("/cvstats leaderboards <leaderboard> edit", "Edit a leaderboard");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length != 0) return sendParamsError(sender);
        Leaderboard leaderboard = (Leaderboard) passedArgs.get(0);

        TextComponent result = new TextComponent();

        TextComponent title = new TextComponent("Editing Leaderboard ");
        title.setColor(ChatColor.YELLOW);
        title.setBold(true);

        title.addExtra(createClickableIcon("✎", "/cvstats leaderboards " + leaderboard.id + " edit", ClickEvent.Action.RUN_COMMAND));

        title.addExtra(createClickableIcon("⟳", "/cvstats leaderboards " + leaderboard.id + " reload", ClickEvent.Action.RUN_COMMAND));

        result.addExtra(title);
        result.addExtra(addBasicRow("id", leaderboard.id));
        result.addExtra(addBasicRow("title", leaderboard.getTitleTextComponent(), "/cvstats leaderboards " + leaderboard.id + " settitle "));
        result.addExtra(addBasicRow("metric", leaderboard.metric, "/cvstats leaderboards " + leaderboard.id + " setmetric "));
        result.addExtra(addBasicRow("key", leaderboard.key, "/cvstats leaderboards " + leaderboard.id + " setkey "));
        result.addExtra(addBasicRow("value", leaderboard.value, "/cvstats leaderboards " + leaderboard.id + " setvalue "));
        result.addExtra(addBasicRow("size", leaderboard.size, "/cvstats leaderboards " + leaderboard.id + " setsize "));
        result.addExtra(addBasicRow("sortBy", leaderboard.sortBy, "/cvstats leaderboards " + leaderboard.id + " sortby "));
        result.addExtra(addBasicRow("updateBy", leaderboard.updateBy));
        result.addExtra(addDisplayList(leaderboard, sender));
        result.addExtra(addFilterList(leaderboard));

        sender.spigot().sendMessage(result);
        return true;
    }

    private TextComponent addBasicRow(String itemName, Object value) {
        return addBasicRow(itemName, value, null);
    }

    private TextComponent addBasicRow(String itemName, Object value, String editCommand) {
        TextComponent result = new TextComponent("\n");

        TextComponent itemComponent = new TextComponent(itemName);
        itemComponent.setColor(value == null ? ChatColor.RED : ChatColor.GREEN);
        result.addExtra(itemComponent);

        result.addExtra(": ");
        if (value instanceof TextComponent) {
            result.addExtra((TextComponent) value);
            result.addExtra(" ");
        } else {
            result.addExtra(value + " ");
        }

        TextComponent editIcon = createClickableIcon("✎", editCommand);
        result.addExtra(editIcon);

        return result;
    }

    private TextComponent addDisplayList(Leaderboard leaderboard, CommandSender sender) {
        TextComponent result = new TextComponent("\n");

        TextComponent displays = new TextComponent("displays ");
        displays.addExtra(createClickableIcon("+", "/cvstats leaderboards " + leaderboard.id + " displays add", ClickEvent.Action.RUN_COMMAND));


        if (leaderboard.getDisplays().size() == 0) {
            displays.setColor(ChatColor.RED);
            result.addExtra(displays);
            result.addExtra(": []");
            return result;
        }

        displays.setColor(ChatColor.GREEN);
        result.addExtra(displays);
        result.addExtra(": [");

        for (int i = 0; i < leaderboard.getDisplays().size(); i++) {
            TextComponent displayTC = new TextComponent("\n  ");
            displayTC.addExtra(createClickableIcon("-", "/cvstats leaderboards " + leaderboard.id + " displays remove " + (i + 1), ClickEvent.Action.RUN_COMMAND, ChatColor.RED));
            displayTC.addExtra(getTeleportTextComponent(sender, leaderboard.getDisplays().get(i)));
            result.addExtra(displayTC);
        }
        result.addExtra("\n]");


        return result;
    }

    private TextComponent addFilterList(Leaderboard leaderboard) {
        TextComponent result = new TextComponent("\n");

        TextComponent filters = new TextComponent("filters ");
        filters.setColor(ChatColor.GREEN);
        result.addExtra(filters);

        result.addExtra(createClickableIcon("+", "/cvstats leaderboards " + leaderboard.id + " filters add "));
        result.addExtra(":");


        if (leaderboard.getFilters().size() == 0) {
            result.addExtra(" {}");
            return result;
        }

        result.addExtra(" {");
        for (String key : leaderboard.getFilters().keySet()) {
            TextComponent filterTC = new TextComponent("\n  ");
            filterTC.addExtra(createClickableIcon("-", "/cvstats leaderboards " + leaderboard.id + " filters remove " + key, ClickEvent.Action.RUN_COMMAND, ChatColor.RED));
            filterTC.addExtra(" " + key + ": " + leaderboard.getFilters().get(key));
            result.addExtra(filterTC);
        }
        result.addExtra("\n}");
        return result;
    }

    private TextComponent createClickableIcon(String icon, String command) {
        return createClickableIcon(icon, command, ClickEvent.Action.SUGGEST_COMMAND);
    }

    private TextComponent createClickableIcon(String icon, String command, ClickEvent.Action action) {
        return createClickableIcon(icon, command, action, ChatColor.AQUA);
    }

    private TextComponent createClickableIcon(String icon, String command, ClickEvent.Action action, ChatColor chatColor) {
        if (command == null) return new TextComponent(); // return empty if no command

        TextComponent result = new TextComponent("[" + icon + "]");
        result.setColor(chatColor);
        result.setBold(true);
        result.setClickEvent(new ClickEvent(action, command));
        return result;
    }

    public TextComponent getTeleportTextComponent(CommandSender sender, Location location) {
        if (!(sender instanceof Player)) return new TextComponent(this.toString());
        Player player = (Player) sender;
        TextComponent result;
        if (player.getLocation().getWorld() == location.getWorld()) {
            result = new TextComponent("[Teleport]");
            result.setColor(ChatColor.AQUA);
            result.setBold(true);
            String tpCoords = location.getX() + " " + location.getY() + " " + location.getZ();
            result.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/minecraft:tp " + player.getDisplayName() + " " + tpCoords));
        } else {
            result = new TextComponent(" In world: " + location.getWorld().getName());
        }
        return result;
    }

}
