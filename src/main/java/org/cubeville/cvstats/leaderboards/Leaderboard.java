package org.cubeville.cvstats.leaderboards;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("Leaderboard")
public class Leaderboard implements ConfigurationSerializable {
    public String id, metric, key;
    public LeaderboardSortBy sortBy = LeaderboardSortBy.COUNT;
    public Integer size;
    List<Display> displays;
    Map<String, String> filters;

    public Leaderboard(String id) { this.id = id; }

    @SuppressWarnings("unchecked")
    public Leaderboard(Map<String, Object> config) {
        this.id = (String) config.get("id");
        this.metric = (String) config.get("metric");
        this.key = (String) config.get("key");
        this.sortBy = LeaderboardSortBy.valueOf((String) config.get("sortBy"));
        this.size = (Integer) config.get("size");
        this.displays = (List<Display>) config.get("displays");
        this.filters = (Map<String, String>) config.get("filters");
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
            put("filters", filters);
        }};
    }

    public void addDisplay(Location location, String title) {
        if (title == null) {
            title = "Leaderboard " + id;
        }
        displays.add(new Display(location, title));
    }

    public void removeDisplay(int i) {
        displays.remove(i);
    }

    public List<Display> getDisplays() {
        return displays;
    }

    public void addFilter(String key, String value) {
        filters.put(key, value);
    }
    public void removeFilter(String key) { filters.remove(key); }
    public boolean hasFilter(String key) { return filters.containsKey(key); }

}
