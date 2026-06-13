package de.regitstudios.survivalGamesReborn.objects;

import de.regitstudios.survivalGamesReborn.GameState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private GameLobby gameLobby;
    private HashMap<UUID, Integer> points;

    public Game(GameLobby gameLobby) {
        this.gameLobby = gameLobby;
        this.points = new HashMap<>();
    }

    public void start() {
        gameLobby.setState(GameState.LIVE);
        gameLobby.sendMessage(ChatColor.GREEN + "GAME HAS STARTED! Break 20 blocks in the fastest time!");

        for(UUID uuid : gameLobby.getPlayers()) {
            points.put(uuid, 0);
        }
    }

    public void addPoint(Player player) {
        int playerPoints = points.get(player.getUniqueId()) + 1;
        if(playerPoints == 20) {
            gameLobby.sendMessage(ChatColor.GOLD + player.getName() + " HAS WON");
            gameLobby.reset(true);
            return;
        }

        player.sendMessage(ChatColor.GREEN + "+1 POINT!");
        points.replace(player.getUniqueId(), playerPoints);
    }
}
