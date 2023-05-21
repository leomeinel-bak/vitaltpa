/*
 * File: VitalTpnoCmd.java
 * Author: Leopold Meinel (leo@meinel.dev)
 * -----
 * Copyright (c) 2023 Leopold Meinel & contributors
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

public class VitalTpnoCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String label, @NotNull String[] args) {
        if (!Cmd.isArgsLengthEqualTo(sender, args, 0)) {
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
