package com.github.invictum;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Charon plugin entry point class
 */
public class Charon extends JavaPlugin implements Listener {

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void onEnable() {
        // Prepare plugin configuration
        saveDefaultConfig();
        getConfig().options().copyHeader(true).copyDefaults(true);
        saveConfig();
        // Register player death listener
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        executor.shutdown();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Runnable task = new Recorder(System.currentTimeMillis(), event);
        executor.submit(task);
    }
}
