package de.regitstudios.survivalGamesReborn.command;

import de.regitstudios.survivalGamesReborn.manager.WaitingQueueManager;
import de.regitstudios.survivalGamesReborn.objects.GameLobby;
import de.regitstudios.survivalGamesReborn.objects.WaitingQueue;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class QueueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(commandSender instanceof Player player) {
            player.sendMessage("Offene Warteschlangen: ");
            for(WaitingQueue waitingQueue : WaitingQueueManager.waitingQueues) {
                player.sendMessage(waitingQueue.getId());
                player.sendMessage("Mit Lobbies: ");
                for(GameLobby lobby : waitingQueue.getGameLobbies()) {
                    player.sendMessage("" + lobby.getId());
                    player.sendMessage(lobby.getPlayers().size() + "/24");
                }
            }
        }
        return false;
    }
}
