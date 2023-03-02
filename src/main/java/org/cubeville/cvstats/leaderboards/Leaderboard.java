package org.cubeville.cvstats.leaderboards;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("Leaderboard")
public class Leaderboard implements ConfigurationSerializable {
    public String id, metric, key;
    LeaderboardSortBy sortBy = LeaderboardSortBy.COUNT;
    Integer size;
    List<Display> displays = new ArrayList<>();

    public Leaderboard(String id) { this.id = id; }
    @SuppressWarnings("unchecked")
    public Leaderboard(Map<String, Object> config) {
        this.id = (String) config.get("id");
        this.metric = (String) config.get("metric");
        this.key = (String) config.get("key");
        this.sortBy = LeaderboardSortBy.valueOf((String) config.get("sortBy"));
        this.size = (Integer) config.get("size");
        this.displays = (List<Display>) config.get("displays");
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>() {{
            put("id", id);
            put("metric", metric);
            put("key", key);
            put("sortby", sortBy);
            put("size", size);
            put("displays", displays);
        }};
    }
}
