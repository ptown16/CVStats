package org.cubeville.cvstats.leaderboards;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("Display")
public class Display implements ConfigurationSerializable {
    Location location;
    String title;

    public Display(Location location, String title) {
        this.location = location;
        this.title = title;
    }

    public Display(Map<String, Object> config) {
        this.location = (Location) config.get("location");
        this.title = (String) config.get("title");
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>() {{
            put("location", location);
            put("title", title);
        }};
    }
}
