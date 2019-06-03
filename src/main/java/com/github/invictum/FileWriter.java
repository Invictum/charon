package com.github.invictum;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Util class for work with history file
 */
public class FileWriter {

    private static final Charon PLUGIN = JavaPlugin.getPlugin(Charon.class);
    private static final Path FILE = Paths.get(PLUGIN.getConfig().getString(Charon.PATH));

    /**
     * Records particular line of data to the file
     * There is an assumption file created and ready to write
     *
     * @param data that represent a line of text
     */
    public static void record(String data) {
        try {
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            Files.write(FILE, bytes, StandardOpenOption.APPEND);
        } catch (IOException e) {
            PLUGIN.getLogger().warning(FILE + " is not writable, please check permissions");
        }
    }

    /**
     * Initially creates a file that will be used to record events
     */
    public static void touch() {
        try {
            Files.createFile(FILE);
        } catch (FileAlreadyExistsException e) {
            PLUGIN.getLogger().info("Using existing file: " + FILE);
        } catch (IOException e) {
            PLUGIN.getLogger().warning("Unable to create a file: " + FILE + ", check permissions");
        }
    }
}
