package de.regitstudios.survivalGamesReborn.objects;

import de.regitstudios.survivalGamesReborn.SurvivalGamesReborn;
import de.regitstudios.survivalGamesReborn.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import static de.regitstudios.survivalGamesReborn.GameState.COUNTDOWN;

public class Countdown extends BukkitRunnable {

    private GameLobby gameLobby;
    private int countdownSeconds;

    public Countdown(GameLobby gameLobby)  {
        this.gameLobby = gameLobby;
        this.countdownSeconds = ConfigManager.getCountdownSeconds();
    }

    public void start() {
        gameLobby.setState(COUNTDOWN);
        runTaskTimer(SurvivalGamesReborn.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if(countdownSeconds == 0) {
            cancel();
            gameLobby.start();
            return;
        }

        if(countdownSeconds <= 10 || countdownSeconds % 15 == 0) {
            gameLobby.sendMessage(ChatColor.GREEN + "Game will start in " + countdownSeconds + " second" + (countdownSeconds == 1 ? "" : "s") + ".");
        }

        gameLobby.sendTitle(ChatColor.GREEN.toString() + countdownSeconds + " second" + (countdownSeconds == 1 ? "" : "s") + ".", "until game starts");

        countdownSeconds--;
    }
}
