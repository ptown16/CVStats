package org.cubeville.cvstats.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cubeville.cvstats.CVStats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SendMetric extends BaseCommand {

    public SendMetric() {
        setPermission("cvstats.send");
        setHelpValue("/cvstats send <metric-name> [...name:value]", "Send a metric to the database");
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
                    try{
                        // if the value of player is a uuid
                        UUID uuid = UUID.fromString(kvPair[0]);
                        fields.put("player", uuid.toString());
                    } catch (IllegalArgumentException exception) {
                        // handle the case where string is not a UUID
                        // if the value of player is an online player, then get the uuid from that
                        Player player = Bukkit.getPlayer(kvPair[1]);
                        if (player == null) return sendError(sender, CommandErrors.playerDoesNotExist(kvPair[1]));
                        fields.put("player", player.getUniqueId().toString());
                    }
                } else {
                    fields.put(kvPair[0], kvPair[1]);
                }
            }
        }
        CVStats.getInstance().sendMetric(metricName, fields);
        return true;
    }
}
