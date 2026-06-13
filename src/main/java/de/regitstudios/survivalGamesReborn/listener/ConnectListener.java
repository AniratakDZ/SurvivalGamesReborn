package de.regitstudios.survivalGamesReborn.listener;

import de.regitstudios.survivalGamesReborn.SurvivalGamesReborn;
import de.regitstudios.survivalGamesReborn.manager.WaitingQueueManager;
import de.regitstudios.survivalGamesReborn.objects.GameLobby;
import de.regitstudios.survivalGamesReborn.manager.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().teleport(ConfigManager.getLobbySpawn());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final GameLobby gameLobby = SurvivalGamesReborn.getInstance().getArenaManager().getArena(event.getPlayer());
        if(gameLobby != null) {
            gameLobby.removePlayer(event.getPlayer());
            WaitingQueueManager.leaveQueue(event.getPlayer());
        }
    }
}