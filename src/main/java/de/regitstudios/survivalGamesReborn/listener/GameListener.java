package de.regitstudios.survivalGamesReborn.listener;

import de.regitstudios.survivalGamesReborn.GameState;
import de.regitstudios.survivalGamesReborn.SurvivalGamesReborn;
import de.regitstudios.survivalGamesReborn.objects.GameLobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GameListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final GameLobby gameLobby = SurvivalGamesReborn.getInstance().getArenaManager().getArena(event.getPlayer());
        if(gameLobby != null) {
            if(gameLobby.getState().equals(GameState.LIVE)) {
                gameLobby.getGame().addPoint(event.getPlayer());
            }
        }
    }
}
