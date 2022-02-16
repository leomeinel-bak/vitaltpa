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

package com.tamrielnetwork.vitaltrash.commands;

import com.tamrielnetwork.vitaltrash.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class VitalTrashCmd implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		// Check args length
		if (args.length != 0) {
			Utils.sendMessage(sender, "invalid-option");
			return true;
		}
		// Toggle Trash Interface
		toggleTrashInterface(sender);
		return true;

	}

	private void toggleTrashInterface(CommandSender sender) {
		// Check if command sender is a player
		if (!(sender instanceof Player)) {
			Utils.sendMessage(sender, "player-only");
			return;
		}
		// Check perms
		if (!sender.hasPermission("vitaltrash.trash")) {
			Utils.sendMessage(sender, "no-perms");
			return;
		}

		Inventory inventory = Bukkit.createInventory((InventoryHolder) sender, 54, Component.text("Trash"));
		((Player) sender).openInventory(inventory);

	}
}