package de.regitstudios.survivalGamesReborn.objects;

import de.regitstudios.survivalGamesReborn.GameState;
import de.regitstudios.survivalGamesReborn.SurvivalGamesReborn;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static java.util.Objects.requireNonNull;

public class WaitingQueue extends BukkitRunnable {

    private String id;

    private List<GameLobby> gameLobbies;
    private List<UUID> waitingPlayers;

    public WaitingQueue(String id, List<GameLobby> gameLobbies) {
        this.id = id;
        this.gameLobbies = gameLobbies;
        waitingPlayers = new ArrayList<>();
    }

    public void start() {
        runTaskTimer(SurvivalGamesReborn.getInstance(), 0, 20);
        Bukkit.getLogger().info("WaitingQueue mit der ID: " + id + " wurde gestartet!");
    }

    public void joinPlayer(Player player) {
        player.sendMessage("Du bist der Warteschlange beigetreten!");
        Bukkit.getLogger().info("Spieler " + player.getName() + " - " + player.getUniqueId() + " ist der Warteschlange " + id + " beigetreten!");
        waitingPlayers.add(player.getUniqueId());
    }

    public void quitPlayer(Player player) {
        player.sendMessage("Du hast die Warteschlange verlassen!");
        Bukkit.getLogger().info("Spieler " + player.getName() + " - " + player.getUniqueId() + " hat die Warteschlange " + id + " verlassen!");
        removePlayer(player.getUniqueId());
    }

    private void removePlayer(UUID uuid) {
        waitingPlayers.remove(uuid);
    }

    @Override
    public void run() {
        if(!waitingPlayers.isEmpty()) {
            final GameLobby nextLobby = getNextPlayableLobby();
            final List<UUID> joinedPlayers = new ArrayList<>();
            if (!(nextLobby == null)) {
                for (UUID playerUuid : waitingPlayers) {
                    if (nextLobby.getPlayers().size() < 24) {
                        nextLobby.addPlayer(requireNonNull(Bukkit.getPlayer(playerUuid)));
                        joinedPlayers.add(playerUuid);
                    }
                }
                for(UUID playerUuid : joinedPlayers) {
                    removePlayer(playerUuid);
                }
            }
        }
    }

    private GameLobby getNextPlayableLobby() {
        final List<GameLobby> playableLobbies = new ArrayList<>();
        for(GameLobby lobby : gameLobbies) {
            if(lobby.getState().equals(GameState.RECRUITING) || lobby.getState().equals(GameState.COUNTDOWN)) {
                playableLobbies.add(lobby);
            }
        }

        if(playableLobbies.isEmpty()) {
            return null;
        }

        final int maxPlayers = playableLobbies.stream().mapToInt(server -> server.getPlayers().size()).max().orElse(0);
        if(maxPlayers == 0) {
            return playableLobbies.get(new Random().nextInt(playableLobbies.size()));
        } else {
            return playableLobbies.stream().max(Comparator.comparingInt(e -> e.getPlayers().size())).orElse(null);
        }
    }

    public String getId() {
        return id;
    }

    public List<GameLobby> getGameLobbies() {
        return gameLobbies;
    }

    public List<UUID> getWaitingPlayers() {
        return waitingPlayers;
    }
}