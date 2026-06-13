package de.regitstudios.survivalGamesReborn;

import de.regitstudios.survivalGamesReborn.command.ArenaCommand;
import de.regitstudios.survivalGamesReborn.command.QueueCommand;
import de.regitstudios.survivalGamesReborn.listener.ConnectListener;
import de.regitstudios.survivalGamesReborn.listener.GameListener;
import de.regitstudios.survivalGamesReborn.manager.ArenaManager;
import de.regitstudios.survivalGamesReborn.manager.ConfigManager;
import de.regitstudios.survivalGamesReborn.manager.WaitingQueueManager;
import de.regitstudios.survivalGamesReborn.objects.WaitingQueue;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class SurvivalGamesReborn extends JavaPlugin {

    private static SurvivalGamesReborn instance;
    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        instance = this;
        ConfigManager.setupConfig();
        arenaManager = new ArenaManager();

        Bukkit.getPluginManager().registerEvents(new ConnectListener(), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
        getCommand("arena").setExecutor(new ArenaCommand());
        getCommand("queue").setExecutor(new QueueCommand());
        WaitingQueueManager.init();
        WaitingQueueManager.start();
    }

    @Override
    public void onDisable() {

    }

    public static SurvivalGamesReborn getInstance() {
        return instance;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }


}