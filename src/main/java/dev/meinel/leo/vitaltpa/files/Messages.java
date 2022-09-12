/*
 * File: Messages.java
 * Author: Leopold Meinel (leo@meinel.dev)
 * -----
 * Copyright (c) 2022 Leopold Meinel & contributors
 * SPDX ID: GPL-3.0-or-later
 * URL: https://www.gnu.org/licenses/gpl-3.0-standalone.html
 * -----
 */

package dev.meinel.leo.vitaltpa.files;

import dev.meinel.leo.vitaltpa.VitalTpa;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Messages {

    private final VitalTpa main = JavaPlugin.getPlugin(VitalTpa.class);
    private final File messagesFile;
    private final FileConfiguration messagesConf;

    public Messages() {
        messagesFile = new File(main.getDataFolder(), "messages.yml");
        saveMessagesFile();
        messagesConf = YamlConfiguration.loadConfiguration(messagesFile);
    }

    private void saveMessagesFile() {
        if (!messagesFile.exists()) {
            main.saveResource("messages.yml", false);
        }
    }

    public FileConfiguration getMessagesConf() {
        return messagesConf;
    }
}
