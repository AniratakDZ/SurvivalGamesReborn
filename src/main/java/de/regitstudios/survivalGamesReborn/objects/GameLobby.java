package de.regitstudios.survivalGamesReborn.objects;

import de.regitstudios.survivalGamesReborn.GameState;
import de.regitstudios.survivalGamesReborn.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameLobby {

    private int id;
    private Location spawn;

    private GameState state;
    private List<UUID> players;
    private Countdown countdown;
    private Game game;

    public GameLobby(int id, Location spawn) {
        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(this);
        this.game = new Game(this);
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if(state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()) {
            countdown.start();
        }
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle("", "");

        if(state == GameState.COUNTDOWN && players.size() < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.RED + "There ist not enough players countdown stopped!");
            reset(false);
        }

        if(state == GameState.LIVE && players.size() < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.RED + "The game has ended as too many players have left!");
            reset(false);
        }
    }

    public void sendMessage(String message) {
        for(UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subTitle) {
        for(UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendTitle(title, subTitle);
        }
    }

    public void start() {
        game.start();
    }

    public void reset(boolean kickplayers) {
        if(kickplayers) {
            final Location location = ConfigManager.getLobbySpawn();
            for(UUID uuid : players) {
                Bukkit.getPlayer(uuid).teleport(location);
            }
            players.clear();
        }
        sendTitle("", "");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public int getId() {
        return id;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Game getGame() {
        return game;
    }
}