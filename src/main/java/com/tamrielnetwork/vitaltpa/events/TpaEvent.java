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

package com.tamrielnetwork.vitaltpa.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TpaEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();
	private Player player;
	private Location location;
	private boolean isCancelled;

	public TpaEvent(@NotNull Player player, @NotNull Location location) {
		this.player = player;
		this.location = location;
	}

	public static @NotNull HandlerList getHandlerList() {
		return HANDLERS;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(@NotNull Player player) {
		this.player = player;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(@NotNull Location location) {
		this.location = location;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;

	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return HANDLERS;
	}
}
