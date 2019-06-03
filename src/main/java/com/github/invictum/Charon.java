package com.github.invictum;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Charon plugin entry point class
 */
public class Charon extends JavaPlugin {

    /**
     * Configuration keys available for plugin
     */
    public static final String PATH = "path";
    public static final String EVENTS = "events";

    static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    @Override
    public void onEnable() {
        // Prepare plugin configuration
        saveDefaultConfig();
        getConfig().options().copyHeader(true).copyDefaults(true);
        saveConfig();
        // Collect events to react on
        List<EventType> types = getConfig().getStringList(EVENTS)
                .stream().map(EventType::valueOf)
                .collect(Collectors.toList());
        // Register listeners
        PluginManager manager = getServer().getPluginManager();
        if (types.contains(EventType.EXP)) {
            manager.registerEvents(new ExpListener(), this);
        }
        if (types.contains(EventType.DEATH) || types.contains(EventType.PVP)) {
            manager.registerEvents(new PvpeListener(), this);
        }
        // Prepare file to work
        FileWriter.touch();
    }

    @Override
    public void onDisable() {
        try {
            EXECUTOR.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            getLogger().warning("Some records won't be saved due to interruption");
        }
    }
}
