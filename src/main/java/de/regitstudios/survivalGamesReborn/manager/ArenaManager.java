package de.regitstudios.survivalGamesReborn.manager;

import de.regitstudios.survivalGamesReborn.SurvivalGamesReborn;
import de.regitstudios.survivalGamesReborn.objects.GameLobby;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private List<GameLobby> gameLobbies = new ArrayList<>();

    public ArenaManager() {
        final FileConfiguration config = SurvivalGamesReborn.getInstance().getConfig();

        for(String str : config.getConfigurationSection("arenas").getKeys(false)) {
            gameLobbies.add(new GameLobby(Integer.parseInt(str), new Location(
                    Bukkit.getWorld(config.getString("arenas." + str + ".world")),
                    config.getDouble("arenas." + str + ".x"),
                    config.getDouble("arenas." + str + ".y"),
                    config.getDouble("arenas." + str + ".z"),
                    (float) config.getDouble("arenas." + str + ".yaw"),
                    (float) config.getDouble("arenas." + str + ".pitch"))
            ));
        }
    }

    public List<GameLobby> getArenas() {
        return gameLobbies;
    }

    public GameLobby getArena(Player player) {
        for(GameLobby gameLobby : gameLobbies) {
            if(gameLobby.getPlayers().contains(player.getUniqueId())) {
                return gameLobby;
            }
        }
        return null;
    }

    public GameLobby getArena(int id) {
        for(GameLobby gameLobby : gameLobbies) {
            if(gameLobby.getId() == id) {
                return gameLobby;
            }
        }
        return null;
    }
}
