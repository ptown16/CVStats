package org.cubeville.cvstats.leaderboards;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Display implements ConfigurationSerializable {
    Location location;
    String title;

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>() {{
            put("location", location);
            put("title", title);
        }};
    }
}
