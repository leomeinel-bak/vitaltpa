/*
 * File: VitalTpa.java
 * Author: Leopold Meinel (leo@meinel.dev)
 * -----
 * Copyright (c) 2022 Leopold Meinel & contributors
 * SPDX ID: GPL-3.0-or-later
 * URL: https://www.gnu.org/licenses/gpl-3.0-standalone.html
 * -----
 */

package dev.meinel.leo.vitaltpa;

import dev.meinel.leo.vitaltpa.commands.VitalTpCancelCmd;
import dev.meinel.leo.vitaltpa.commands.VitalTpaCmd;
import dev.meinel.leo.vitaltpa.commands.VitalTpahereCmd;
import dev.meinel.leo.vitaltpa.commands.VitalTpnoCmd;
import dev.meinel.leo.vitaltpa.commands.VitalTpyesCmd;
import dev.meinel.leo.vitaltpa.files.Messages;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VitalTpa extends JavaPlugin {

  private Messages messages;

  @Override
  public void onEnable() {
    registerCommands();
    saveDefaultConfig();
    messages = new Messages();
    Bukkit
        .getLogger()
        .info("VitalTpa v" + this.getDescription().getVersion() + " enabled");
    Bukkit.getLogger().info("Copyright (C) 2022 Leopold Meinel");
    Bukkit.getLogger().info("This program comes with ABSOLUTELY NO WARRANTY!");
    Bukkit
        .getLogger()
        .info(
            "This is free software, and you are welcome to redistribute it under certain conditions.");
    Bukkit
        .getLogger()
        .info(
            "See https://github.com/LeoMeinel/VitalTpa/blob/main/LICENSE for more details.");
  }

  @Override
  public void onDisable() {
    Bukkit
        .getLogger()
        .info("VitalTpa v" + this.getDescription().getVersion() + " disabled");
  }

  private void registerCommands() {
    Objects.requireNonNull(getCommand("tpa")).setExecutor(new VitalTpaCmd());
    Objects
        .requireNonNull(getCommand("tpahere"))
        .setExecutor(new VitalTpahereCmd());
    Objects.requireNonNull(getCommand("tpno")).setExecutor(new VitalTpnoCmd());
    Objects
        .requireNonNull(getCommand("tpyes"))
        .setExecutor(new VitalTpyesCmd());
    Objects
        .requireNonNull(getCommand("tpcancel"))
        .setExecutor(new VitalTpCancelCmd());
  }

  public Messages getMessages() {
    return messages;
  }
}
