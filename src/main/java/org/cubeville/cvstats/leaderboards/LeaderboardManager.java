package org.cubeville.cvstats.leaderboards;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.cubeville.cvstats.CVStats;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardManager implements ConfigurationSerializable {

    Map<String, Leaderboard> leaderboards;

    public LeaderboardManager(Map<String, Object> config) {
        leaderboards = (Map<String, Leaderboard>) config.get("leaderboards");
    }

    public LeaderboardManager() {
        leaderboards = new HashMap<>();
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>() {{
            put("leaderboards", leaderboards);
        }};
    }

    public Leaderboard getLeaderboard(String leaderboardName) {
        return leaderboards.get(leaderboardName);
    }

    public void createLeaderboard(String leaderboardName) {
        leaderboards.put(leaderboardName, new Leaderboard(leaderboardName));
        CVStats.getInstance().saveLeaderboardManager();
    }

    public void deleteLeaderboard(String leaderboardName) {
        leaderboards.remove(leaderboardName);
        CVStats.getInstance().saveLeaderboardManager();
    }

    public boolean hasLeaderboard(String leaderboardName) {
        return leaderboards.containsKey(leaderboardName);
    }
}
