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
 * along with this program. If not, see https://github.com/TamrielNetwork/VitalTpa/blob/main/LICENSE
 */

package com.tamrielnetwork.vitaltpa.commands;

import com.tamrielnetwork.vitaltpa.utils.Chat;
import com.tamrielnetwork.vitaltpa.utils.commands.Cmd;
import com.tamrielnetwork.vitaltpa.utils.commands.CmdSpec;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class VitalTpnoCmd
		implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String[] args) {
		if (Cmd.isArgsLengthNotEqualTo(sender, args, 0)) {
			return false;
		}
		doTpno(sender);
		return true;
	}

	public void doTpno(@NotNull CommandSender sender) {
		if (Cmd.isInvalidSender(sender)) {
			return;
		}
		Player senderPlayer = (Player) sender;
		Player player = CmdSpec.getPlayerKeyInMap(senderPlayer);
		if (CmdSpec.isInvalidCmd(sender, player, "vitaltpa.tpno", true)) {
			return;
		}
		assert player != null;
		CmdSpec.clearMaps(player);
		Chat.sendMessage(sender, Map.of("%player%", player.getName()), "tpa-no");
		Chat.sendMessage(player, Map.of("%player%", sender.getName()), "tpa-denied");
	}
}
