/*
 * VitalTpa is a Spigot Plugin that gives players the ability to teleport to each other.
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
 * along with this program. If not, see https://github.com/TamrielNetwork/VitalTp/blob/main/LICENSE
 */

package com.tamrielnetwork.vitaltpa.commands;

import com.google.common.collect.ImmutableMap;
import com.tamrielnetwork.vitaltpa.VitalTpa;
import com.tamrielnetwork.vitaltpa.events.TpaEvent;
import com.tamrielnetwork.vitaltpa.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class VitalTpaCmd implements TabExecutor {

	private final VitalTpa main = JavaPlugin.getPlugin(VitalTpa.class);

	static final HashMap<UUID, UUID> tpPlayerMap = new HashMap<>();
	static final HashMap<HashMap<UUID, UUID>, String> tpMap = new HashMap<>();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		// Check args length
		if (args.length < 1) {
			Utils.sendMessage(sender, "no-args");
			return true;
		}
		// Check arg 0
		switch (args[0]) {
			case "tpa" -> requestTpa(sender, args);
			case "tpahere" -> requestTpaHere(sender, args);
			case "tpyes" -> doTpRequest(false, sender, args);
			case "tpno" -> doTpRequest(true, sender, args);
			default -> Utils.sendMessage(sender, "invalid-option");
		}
		return true;
	}

	public void requestTpa(CommandSender sender, String[] args) {
		if (isInvalidTpa(sender, args, "vitaltpa.tpa")) {
			return;
		}
		Player player = Bukkit.getPlayer(args[1]);
		assert player != null;
		Utils.sendMessage(player, ImmutableMap.of("%player%", player.getName()), "tpa-received");
		Utils.sendMessage(sender, ImmutableMap.of("%player%", player.getName()), "tpa-sent");
		doTiming(sender);
	}

	public void requestTpaHere(CommandSender sender, String[] args) {
		if (isInvalidTpa(sender, args, "vitaltpa.tpahere")) {
			return;
		}
		Player player = Bukkit.getPlayer(args[1]);
		assert player != null;
		Utils.sendMessage(player, ImmutableMap.of("%player%", player.getName()), "tpahere-received");
		Utils.sendMessage(sender, ImmutableMap.of("%player%", player.getName()), "tpa-sent");
		doTiming(sender);
	}

	public void doTpRequest(Boolean cancel, CommandSender sender, String[] args) {
		if (args.length > 1) {
			clearMaps(sender);
			Utils.sendMessage(sender, "invalid-option");
			return;
		}
		if (cancel && !sender.hasPermission("vitaltpa.tpno")) {
			Utils.sendMessage(sender, "no-perms");
			return;
		}
		if (cancel) {
			clearMaps(sender);
			Utils.sendMessage(sender, "tpa-denied");
			return;
		}
		if (!sender.hasPermission("vitaltpa.tpyes")) {
			Utils.sendMessage(sender, "no-perms");
			return;
		}
		if (!tpPlayerMap.containsKey(((Player) sender).getUniqueId())) {
			Utils.sendMessage(sender, "no-request");
			return;
		}
		for (Map.Entry<UUID, UUID> uuidEntry : tpPlayerMap.entrySet()) {
			if (uuidEntry.getValue().equals(((Player) sender).getUniqueId())) {
				Player player = Bukkit.getPlayer(uuidEntry.getKey());
				assert player != null;
				TpaEvent event = new TpaEvent(player, player.getLocation());
				Bukkit.getPluginManager().callEvent(event);
				doTpa(sender, player);
				clearMaps(Bukkit.getPlayer(uuidEntry.getKey()));
				break;
			}
		}
		Utils.sendMessage(sender, "tpa-accepted");
	}

	private void doTpa(CommandSender sender, Player player) {
		for (Map.Entry<HashMap<UUID,UUID>, String> tpEntry : tpMap.entrySet()) {
			if (tpEntry.getValue().equals("tpa")) {
				player.teleport(((Player) sender).getLocation());
				Utils.sendMessage(sender, ImmutableMap.of("%player%", player.getName()), "tpa-done");
				return;
			}
			if (tpEntry.getValue().equals("tpahere")) {
				((Player) sender).teleport(player.getLocation());
				Utils.sendMessage(sender, ImmutableMap.of("%player%", player.getName()), "tpahere-done");
				return;
			}
			break;
		}
	}

	private boolean isInvalidTpa(CommandSender sender, String[] args, String perm) {
		if (args.length > 2) {
			Utils.sendMessage(sender, "invalid-option");
			return true;
		}
		// Check if command sender is a player
		if (!(sender instanceof Player)) {
			Utils.sendMessage(sender, "player-only");
			return true;
		}
		// Check perms
		if (!sender.hasPermission(perm)) {
			Utils.sendMessage(sender, "no-perms");
			return true;
		}
		if (isInValidPlayer(sender, args)) {
			return true;
		}
		Player player = Bukkit.getPlayer(args[1]);
		assert player != null;
		if (tpPlayerMap.containsKey(((Player) sender).getUniqueId())) {
			Utils.sendMessage(sender, "active-tpa");
			return true;
		}
		tpPlayerMap.put(((Player) sender).getUniqueId(), player.getUniqueId());
		if (Objects.equals(args[0], "tpa")) {
			tpMap.put(tpPlayerMap, "tpa");
			return false;
		}
		if (Objects.equals(args[0], "tpahere")) {
			tpMap.put(tpPlayerMap, "tpahere");
		}
		return false;
	}

	private boolean isInValidPlayer(CommandSender sender, String[] args) {
		if (Bukkit.getPlayer(args[1]) == null || Bukkit.getPlayer(args[1]) == sender) {
			Utils.sendMessage(sender, "invalid-player");
			return true;
		}
		Player player = Bukkit.getPlayer(args[1]);
		if (!Objects.requireNonNull(player).isOnline()) {
			Utils.sendMessage(sender, "not-online");
			return true;
		}
		return false;
	}

	private void doTiming(CommandSender sender) {
		new BukkitRunnable() {
			@Override
			public void run() {
				clearMaps(sender);
			}
		}.runTaskLaterAsynchronously(main, main.getConfig().getLong("request-expiry"));
	}

	private void clearMaps(CommandSender sender) {
		tpMap.remove(tpPlayerMap);
		tpPlayerMap.remove(((Player) sender).getUniqueId());
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		@Nullable List<String> tabComplete = new ArrayList<>();
		if (args.length == 1) {
			if (sender.hasPermission("vitaltpa.tp")) {
				tabComplete.add("tpa");
			}
			if (sender.hasPermission("vitaltpa.tphere")) {
				tabComplete.add("tpahere");
			}
			if (sender.hasPermission("vitaltpa.tpyes")) {
				tabComplete.add("tpyes");
			}
			if (sender.hasPermission("vitaltpa.tpno")) {
				tabComplete.add("tpno");
			}
		} else {
			tabComplete = null;
		}
		return tabComplete;
	}

}