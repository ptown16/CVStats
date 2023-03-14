package org.cubeville.cvstats.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cubeville.cvstats.CVStats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClearMetric extends BaseCommand {

    public ClearMetric() {
        setPermission("cvstats.clear");
        setHelpValue("/cvstats clear <metric-name> [...name:value]", "Clear a metric from the database");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length < 1) return sendParamsError(sender);
        String metricName = args[0].toLowerCase();
        Map<String, String> fields = new HashMap<>();
        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                String[] kvPair = args[i].toLowerCase().split(":");
                if (kvPair.length != 2) return sendError(sender, CommandErrors.COLON_KEY_VALUE);
                if (kvPair[0].equals("player")) {
                    Player player = Bukkit.getPlayer(kvPair[1]);
                    if (player == null) {
                        fields.put(kvPair[0], kvPair[1]);
                    } else {
                        fields.put("player", player.getUniqueId().toString());
                    }
                } else {
                    fields.put(kvPair[0], kvPair[1]);
                }
            }
        }
        CVStats.getInstance().clearMetric(metricName, fields);
        sendSuccess(sender, "Cleared metric " + metricName + " with fields " + fields + "! NOTE: You'll need to do a manual leaderboard reload to see the changes reflected there.");
        return true;
    }
}
