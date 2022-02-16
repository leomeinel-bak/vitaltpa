/*
 * VitalTrash is a Spigot Plugin that gives players the ability to open a trash interface.
 * Copyright © 2022 Leopold Meinel
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
 * along with this program. If not, see https://github.com/TamrielNetwork/VitalTrash/blob/main/LICENSE
 */

package com.tamrielnetwork.vitaltrash.utils;

import com.tamrielnetwork.vitaltrash.VitalTrash;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Utils {

	private static final VitalTrash main = JavaPlugin.getPlugin(VitalTrash.class);

	public static void sendMessage(CommandSender player, String message) {
		player.sendMessage(replaceColors(Objects.requireNonNull(main.getMessages().getMessagesConf().getString(message))));
	}

	public static String replaceColors(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

}