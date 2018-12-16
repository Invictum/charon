package com.github.invictum;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Records particular player death event
 */
public class Recorder implements Runnable {

    private static final Charon PLUGIN = JavaPlugin.getPlugin(Charon.class);

    private long timestamp;
    private PlayerDeathEvent event;

    public Recorder(long timestamp, PlayerDeathEvent event) {
        this.timestamp = timestamp;
        this.event = event;
    }

    @Override
    public void run() {
        Player dead = event.getEntity();
        if (dead != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(timestamp).append(",").append(dead.getName());
            if (dead.getKiller() != null) {
                builder.append(",").append(dead.getKiller().getName());
            }
            builder.append(System.lineSeparator());
            recordToFile(builder.toString());
        }
    }

    private void recordToFile(String data) {
        Path file = Paths.get(PLUGIN.getConfig().getString("path"));
        // Create file for dumping
        if (Files.notExists(file)) {
            try {
                Files.createFile(file);
            } catch (IOException e) {
                PLUGIN.getLogger().warning("Unable to create file: " + file + ", check permissions");
            }
        }
        // Record data
        try {
            Files.write(file, data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            PLUGIN.getLogger().warning(file + " is not writable, check permissions");
        }
    }
}
