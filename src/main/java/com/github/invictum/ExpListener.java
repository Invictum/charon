package com.github.invictum;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

/**
 * Records an event related to experience gaining
 */
public class ExpListener implements Listener {

    @EventHandler
    public void onExp(PlayerExpChangeEvent event) {
        long timestamp = System.currentTimeMillis();
        Charon.EXECUTOR.submit(() -> {
            Player player = event.getPlayer();
            if (player != null) {
                String row = timestamp + "," + EventType.EXP + "," + player.getPlayerListName() + ","
                        + event.getAmount() + System.lineSeparator();
                FileWriter.record(row);
            }
        });
    }
}
