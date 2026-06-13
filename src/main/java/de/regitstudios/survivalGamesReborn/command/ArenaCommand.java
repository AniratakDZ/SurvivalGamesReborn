package de.regitstudios.survivalGamesReborn.command;

import de.regitstudios.survivalGamesReborn.manager.WaitingQueueManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ArenaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(commandSender instanceof Player player) {
            WaitingQueueManager.joinQueue(player, "unranked_modern_sg");
        }
        return false;
    }
}
