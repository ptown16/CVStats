package org.cubeville.cvstats.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.cubeville.cvstats.CVStats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimestampMigration extends BaseCommand {

    public TimestampMigration() {
        setPermission("cvstats.leaderboards.timestampmigration");
        setHelpValue("/cvstats timestampmigration", "Migrate timestamps from oct to dec");
    }

    @Override
    protected boolean runCommand(CommandSender sender, String[] args, List<Object> passedArgs) {
        Map<String, Long> convertedTimestamps = new HashMap<>();
        ResultSet resultSet = CVStats.getDatabase().getOctalTimestamps();
        try {
            while (resultSet.next()) {
                String metricID = resultSet.getString("metric_id");
                String timestamp = resultSet.getString("timestamp");
                long decimal = Long.parseLong(timestamp, 8);
                Bukkit.getLogger().info("Updating timestamp of metric " + metricID + " to be " + decimal);
                convertedTimestamps.put(metricID, decimal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return sendError(sender,"SQL error");
        }
        if (convertedTimestamps.size() == 0) { return sendError(sender,"There are no octal timestamps to migrate!"); }
        CVStats.getDatabase().batchUpdateOctalTimestamps(convertedTimestamps);
        sendSuccess(sender, "Converted!");
        return true;
    }
}
