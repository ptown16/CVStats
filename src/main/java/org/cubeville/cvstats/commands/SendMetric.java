package org.cubeville.cvstats.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cubeville.cvstats.CVStats;

import java.util.*;

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
                        UUID uuid = UUID.fromString(kvPair[1]);
                        fields.put("player", uuid.toString());
                    } catch (IllegalArgumentException exception) {
                        // handle the case where string is not a UUID
                        // if the value of player is an online player, then get the uuid from that
                        Player player = Bukkit.getPlayer(kvPair[1]);
                        if (player == null) return sendError(sender, CommandErrors.playerDoesNotExist(kvPair[1]));
                        fields.put("player", player.getUniqueId().toString());
                    }
                } else if (kvPair[0].equals("players")) {
                    try{
                        // if the value of the players are uuids
                        List<UUID> playerUUIDs = new ArrayList<>();
                        for (String string : kvPair[1].split(",")) {
                            playerUUIDs.add(UUID.fromString(string));
                        }
                        List<String> playerUUIDStrings = new ArrayList<>();
                        for (UUID uuid : playerUUIDs) {
                            playerUUIDStrings.add(uuid.toString());
                        }
                        playerUUIDStrings.sort(Comparator.comparing(o -> o));
                        StringJoiner joiner = new StringJoiner(",");
                        playerUUIDStrings.forEach(joiner::add);
                        String uuids = joiner.toString();
                        fields.put("players", uuids);
                    } catch (IllegalArgumentException exception) {
                        // handle the case where string is not a UUID
                        // if the value of player is an online player, then get the uuid from that
                        List<String> playerUUIDs = new ArrayList<>();
                        for (String playerName : kvPair[1].split(",")) {
                            Player player = Bukkit.getPlayer(playerName);
                            if (player == null) return sendError(sender, CommandErrors.playerDoesNotExist(kvPair[1]));
                            playerUUIDs.add(player.getUniqueId().toString());
                        }
                        playerUUIDs.sort(Comparator.comparing(o -> o));
                        StringJoiner joiner = new StringJoiner(",");
                        playerUUIDs.forEach(joiner::add);
                        String uuids = joiner.toString();
                        fields.put("players", uuids);
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
