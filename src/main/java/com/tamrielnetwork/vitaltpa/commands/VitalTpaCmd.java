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
import com.tamrielnetwork.vitaltpa.utils.Chat;
import com.tamrielnetwork.vitaltpa.utils.Cmd;
import com.tamrielnetwork.vitaltpa.utils.CmdSpec;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.tamrielnetwork.vitaltpa.utils.CmdSpec.tpPlayerMap;

public class VitalTpaCmd implements TabExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (Cmd.checkArgsLessThan(sender, args, 1)) {
			return true;
		}

		switch (args[0]) {
			case "tpa" -> doRequest(sender, args, "vitaltpa.tpa", "tpa-received");
			case "tpahere" -> doRequest(sender, args,"vitaltpa.tpahere", "tpahere-received");
			case "tpyes" -> handleRequest(false, sender, args);
			case "tpno" -> handleRequest(true, sender, args);
			default -> Chat.sendMessage(sender, "cmd");
		}
		return true;
	}

	public void doRequest(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String perm, @NotNull String playerMessage) {
		Player player = Bukkit.getPlayer(args[1]);

		if (player == null) {
			Chat.sendMessage(sender, "not-online");
			return;
		}

		if (CmdSpec.isInvalidTpa(sender, args, player, perm, 2)) {
			return;
		}

		CmdSpec.addToMap(sender, args, playerMessage, "tpa-sent");
	}

	public void handleRequest(@NotNull Boolean cancel, @NotNull CommandSender sender, @NotNull String[] args) {
		Player senderPlayer = (Player) sender;
		Player player = CmdSpec.getMappedPlayer(senderPlayer);
		if (!tpPlayerMap.containsValue(senderPlayer.getUniqueId())) {
			Chat.sendMessage(senderPlayer, "no-request");
			return;
		}
		if (player == null) {
			Chat.sendMessage(sender, "not-online");
			return;
		}
		if (cancel && CmdSpec.isInvalidTpa(sender, args, player, "vitaltpa.tpno", 1)) {
			return;
		}
		if (cancel) {
			CmdSpec.clearMaps(player);
			Chat.sendMessage(sender, ImmutableMap.of("%player%", player.getName()), "tpa-no");
			Chat.sendMessage(player, ImmutableMap.of("%player%", sender.getName()), "tpa-denied");
			return;
		}
		if (CmdSpec.isInvalidTpa(player, args, senderPlayer, "vitaltpa.tpyes", 1)) {
			return;
		}
		CmdSpec.doUnmap(senderPlayer, player);
		Chat.sendMessage(sender, ImmutableMap.of("%player%", player.getName()), "tpa-yes");
		Chat.sendMessage(player, ImmutableMap.of("%player%", sender.getName()), "tpa-accepted");
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