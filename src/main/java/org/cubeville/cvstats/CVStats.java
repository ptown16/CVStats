package org.cubeville.cvstats;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.cubeville.cvstats.commands.CommandHandler;
import org.cubeville.cvstats.database.StatsDB;

import java.util.Map;
import java.util.logging.Level;

public final class CVStats extends JavaPlugin implements CommandExecutor {

    public static CVStats instance;
    public static StatsDB db;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        saveDefaultConfig();

        FileConfiguration config = getConfig();
        String hostname = config.getString("mysql-hostname");
        int port = config.getInt("mysql-port");
        String database = config.getString("mysql-database");
        String username = config.getString("mysql-username");
        String password = config.getString("mysql-password");
        if (hostname != null && port != 0 && database != null && username != null && password != null ) {
            db = new StatsDB(hostname, port, database, username, password);
            db.load();
        } else {
            getLogger().log(Level.SEVERE, "CVStats is not enabled -- please fill your config with the correct parameters for your MySQL database!");
            return;
        }
        this.getCommand("cvstats").setExecutor(new CommandHandler());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (db != null) {
            db.closePool();
        }
    }

    public void sendMetric(String metricName, Player player) {
        sendMetric(metricName, player, Map.of());
    }

    public void sendMetric(String metricName, Player player, Map<String, String> fields) {
        db.sendMetricEvent(metricName, player, fields);
    }

    public static CVStats getInstance() {
        return instance;
    }

}
