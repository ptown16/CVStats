package org.cubeville.cvstats.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cubeville.cvstats.CVStats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendMetricCommand extends BaseCommand {

    public SendMetricCommand(String permission) {
        super(permission);
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        if (args.length < 1) return sendError(sender, "Incorrect arg length, did you mean to do \"/cvstats send <metric-name> [...name:value]\" ?");
        String metricName = args[0].toLowerCase();
        Map<String, String> fields = new HashMap<>();
        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                String[] kvPair = args[i].toLowerCase().split(":");
                if (kvPair.length != 2) return sendError(sender, "There must be exactly 1 colon when defining an extra field");
                fields.put(kvPair[0].toLowerCase(), kvPair[1]);
            }
        }
        CVStats.getInstance().sendMetric(metricName, fields);
        return true;
    }
}
