package com.github.invictum;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Records events related to PvP or player's death
 */
public class PvpeListener implements Listener {

    @EventHandler
    public void onExp(PlayerDeathEvent event) {
        long timestamp = System.currentTimeMillis();
        Charon.EXECUTOR.submit(() -> {
            Player victim = event.getEntity();
            if (victim != null) {
                String row = timestamp + ",";
                if (victim.getKiller() == null) {
                    row += EventType.DEATH + "," + victim.getName();
                } else {
                    row += EventType.PVP + "," + victim.getName() + "," + victim.getKiller().getName();
                }
                row += System.lineSeparator();
                FileWriter.record(row);
            }
        });
    }
}
