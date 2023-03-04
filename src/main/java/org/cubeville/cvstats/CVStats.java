package org.cubeville.cvstats;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.cubeville.cvstats.commands.CommandHandler;
import org.cubeville.cvstats.database.StatsDB;
import org.cubeville.cvstats.leaderboards.Leaderboard;
import org.cubeville.cvstats.leaderboards.LeaderboardManager;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public final class CVStats extends JavaPlugin implements CommandExecutor {

    private static StatsDB db;
    private static CVStats instance;
    public static LeaderboardManager leaderboardManager;

    @Override
    public void onEnable() {
        instance = this;

        // database setup
        db = new StatsDB("stats");
        try {
            db.createBackup();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        db.load();

        saveDefaultConfig();

        ConfigurationSerialization.registerClass(LeaderboardManager.class, "LeaderboardManager");
        ConfigurationSerialization.registerClass(Leaderboard.class, "Leaderboard");

        leaderboardManager = (LeaderboardManager) getConfig().get("LeaderboardManager");
        if(leaderboardManager == null) leaderboardManager = new LeaderboardManager();

        this.getCommand("cvstats").setExecutor(new CommandHandler());

    }

    public void saveLeaderboardManager() {
        getConfig().set("LeaderboardManager", leaderboardManager);
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (db != null) {
            db.disconnect();
        }
    }

    public void sendMetric(String metricName) {
        sendMetric(metricName, Map.of());
    }

    public void sendMetric(String metricName, Map<String, String> fields) {
        db.sendMetricEvent(metricName, fields);
    }

    public static StatsDB getDatabase() {
        return db;
    }

    public static CVStats getInstance() {
        return instance;
    }

    public static LeaderboardManager getLeaderboards() {
        return leaderboardManager;
    }
}
