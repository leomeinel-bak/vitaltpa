/*
 * VitalTpa is a Spigot Plugin that gives players the ability to ask players to teleport to them.
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
 * along with this program. If not, see https://github.com/TamrielNetwork/VitalTpa/blob/main/LICENSE
 */

package com.tamrielnetwork.vitaltpa.utils.commands;

import com.google.common.collect.ImmutableMap;
import com.tamrielnetwork.vitaltpa.VitalTpa;
import com.tamrielnetwork.vitaltpa.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CmdSpec {

	private static final HashMap<UUID, UUID> tpPlayerMap = new HashMap<>();
	private static final HashMap<HashMap<UUID, UUID>, String> tpMap = new HashMap<>();
	private static final VitalTpa main = JavaPlugin.getPlugin(VitalTpa.class);

	public static void addToMap(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String playerMessage, @NotNull String senderMessage) {
		Player player = Bukkit.getPlayer(args[1]);
		Player senderPlayer = (Player) sender;
		assert player != null;

		if (tpPlayerMap.containsKey(senderPlayer.getUniqueId())) {
			Chat.sendMessage(sender, "active-tpa");
			return;
		}
		tpPlayerMap.put(senderPlayer.getUniqueId(), player.getUniqueId());
		switch (args[0]) {
			case "tpa" -> tpMap.put(tpPlayerMap, "tpa");
			case "tpahere" -> tpMap.put(tpPlayerMap, "tpahere");
			default -> Chat.sendMessage(sender, "invalid-option");
		}
		Chat.sendMessage(player, ImmutableMap.of("%player%", sender.getName()), playerMessage);
		Chat.sendMessage(sender, ImmutableMap.of("%player%", player.getName()), senderMessage);
		doTiming(sender);
	}

	public static void doUnmap(@NotNull Player senderPlayer, @NotNull Player player) {
		for (Map.Entry<UUID, UUID> uuidEntry : tpPlayerMap.entrySet()) {
			if (uuidEntry.getValue().equals(senderPlayer.getUniqueId())) {
				doTpa(senderPlayer, player);
				clearMaps(Objects.requireNonNull(Bukkit.getPlayer(uuidEntry.getKey())));
				break;
			}
		}
	}

	public static void clearMaps(@NotNull CommandSender sender) {
		Player senderPlayer = (Player) sender;
		tpMap.remove(tpPlayerMap);
		tpPlayerMap.remove(senderPlayer.getUniqueId());
	}

	private static void doTiming(@NotNull CommandSender sender) {
		new BukkitRunnable() {
			@Override
			public void run() {
				clearMaps(sender);
			}
		}.runTaskLaterAsynchronously(main, (main.getConfig().getLong("request-expiry") * 20L));
	}

	private static void doTpa(@NotNull CommandSender sender, @NotNull Player player) {
		Player senderPlayer = (Player) sender;
		for (Map.Entry<HashMap<UUID, UUID>, String> tpEntry : tpMap.entrySet()) {
			if (tpEntry.getValue().equals("tpa")) {
				player.teleport(senderPlayer.getLocation());
				Chat.sendMessage(player, ImmutableMap.of("%player%", sender.getName()), "tpa-done");
				return;
			}
			if (tpEntry.getValue().equals("tpahere")) {
				senderPlayer.teleport(player.getLocation());
				Chat.sendMessage(player, ImmutableMap.of("%player%", sender.getName()), "tpahere-done");
				return;
			}
			break;
		}
	}

	public static boolean isInvalidCmd(@NotNull CommandSender sender, @NotNull String[] args, Player player, @NotNull String perm, int length) {
		if (Cmd.isArgsLengthGreaterThan(sender, args, length)) {
			return true;
		}
		if (Cmd.isInvalidSender(sender)) {
			return true;
		}
		if (Cmd.isNotPermitted(sender, perm)) {
			return true;
		}
		return Cmd.isInvalidPlayer(sender, player);
	}

	public static Player getMappedPlayer(@NotNull Player senderPlayer) {
		for (Map.Entry<UUID, UUID> uuidEntry : tpPlayerMap.entrySet()) {
			if (uuidEntry.getValue().equals(senderPlayer.getUniqueId())) {
				return Bukkit.getPlayer(uuidEntry.getKey());
			}
		}
		return null;
	}

	public static HashMap<UUID, UUID> getTpPlayerMap() {
		return tpPlayerMap;
	}

}
