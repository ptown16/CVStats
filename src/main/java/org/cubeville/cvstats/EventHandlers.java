package org.cubeville.cvstats;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventHandlers implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        CVStats.getInstance().savePlayerName(e.getPlayer());
    }

}
