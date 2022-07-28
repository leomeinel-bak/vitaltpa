/*
 * VitalTpa is a Spigot Plugin that gives players the ability to ask players to teleport to them.
 * Copyright © 2022 Leopold Meinel & contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see https://github.com/LeoMeinel/VitalTpa/blob/main/LICENSE
 */

package com.tamrielnetwork.vitaltpa;

import com.tamrielnetwork.vitaltpa.commands.VitalTpCancelCmd;
import com.tamrielnetwork.vitaltpa.commands.VitalTpaCmd;
import com.tamrielnetwork.vitaltpa.commands.VitalTpahereCmd;
import com.tamrielnetwork.vitaltpa.commands.VitalTpnoCmd;
import com.tamrielnetwork.vitaltpa.commands.VitalTpyesCmd;
import com.tamrielnetwork.vitaltpa.files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class VitalTpa
		extends JavaPlugin {

	private Messages messages;

	@Override
	public void onEnable() {
		registerCommands();
		saveDefaultConfig();
		messages = new Messages();
		Bukkit.getLogger()
		      .info("VitalTpa v" + this.getDescription()
		                               .getVersion() + " enabled");
		Bukkit.getLogger()
		      .info("Copyright (C) 2022 Leopold Meinel");
		Bukkit.getLogger()
		      .info("This program comes with ABSOLUTELY NO WARRANTY!");
		Bukkit.getLogger()
		      .info("This is free software, and you are welcome to redistribute it under certain conditions.");
		Bukkit.getLogger()
		      .info("See https://github.com/LeoMeinel/VitalTpa/blob/main/LICENSE for more details.");
	}

	@Override
	public void onDisable() {
		Bukkit.getLogger()
		      .info("VitalTpa v" + this.getDescription()
		                               .getVersion() + " disabled");
	}

	private void registerCommands() {
		Objects.requireNonNull(getCommand("tpa"))
		       .setExecutor(new VitalTpaCmd());
		Objects.requireNonNull(getCommand("tpahere"))
		       .setExecutor(new VitalTpahereCmd());
		Objects.requireNonNull(getCommand("tpno"))
		       .setExecutor(new VitalTpnoCmd());
		Objects.requireNonNull(getCommand("tpyes"))
		       .setExecutor(new VitalTpyesCmd());
		Objects.requireNonNull(getCommand("tpcancel"))
		       .setExecutor(new VitalTpCancelCmd());
	}

	public Messages getMessages() {
		return messages;
	}
}

