package org.cubeville.cvstats.commands;

import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                fields.put(kvPair[0].toLowerCase(), kvPair[1]);
            }
        }
        CVStats.getInstance().sendMetric(metricName, fields);
        return true;
    }
}
