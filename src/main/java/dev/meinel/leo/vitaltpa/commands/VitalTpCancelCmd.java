/*
 * File: VitalTpCancelCmd.java
 * Author: Leopold Meinel (leo@meinel.dev)
 * -----
 * Copyright (c) 2022 Leopold Meinel & contributors
 * SPDX ID: GPL-3.0-or-later
 * URL: https://www.gnu.org/licenses/gpl-3.0-standalone.html
 * -----
 */

package dev.meinel.leo.vitaltpa.commands;

import dev.meinel.leo.vitaltpa.utils.Chat;
import dev.meinel.leo.vitaltpa.utils.commands.Cmd;
import dev.meinel.leo.vitaltpa.utils.commands.CmdSpec;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VitalTpCancelCmd implements CommandExecutor {

  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {
    if (Cmd.isArgsLengthNotEqualTo(sender, args, 0)) {
      return false;
    }
    doTpCancel(sender);
    return true;
  }

  public void doTpCancel(@NotNull CommandSender sender) {
    if (Cmd.isInvalidSender(sender)) {
      return;
    }
    Player senderPlayer = (Player) sender;
    Player player = CmdSpec.getPlayerValueInMap(senderPlayer);
    if (player == null) {
      Chat.sendMessage(sender, "no-request");
      return;
    }
    if (CmdSpec.isInvalidCmd(sender, player, "vitaltpa.tpcancel")) {
      return;
    }
    CmdSpec.doUnmap(player);
    Chat.sendMessage(
        sender,
        Map.of("%player%", senderPlayer.getName()),
        "tpa-cancelled");
    Chat.sendMessage(
        player,
        Map.of("%player%", senderPlayer.getName()),
        "tpa-cancelled");
  }
}
