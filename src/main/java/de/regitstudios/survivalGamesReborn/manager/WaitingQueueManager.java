package de.regitstudios.survivalGamesReborn.manager;

import de.regitstudios.survivalGamesReborn.SurvivalGamesReborn;
import de.regitstudios.survivalGamesReborn.objects.WaitingQueue;
import org.bukkit.entity.Player;

import java.util.*;

public class WaitingQueueManager {

    public static final List<WaitingQueue> waitingQueues = new ArrayList<>();
    private static final Map<UUID, String> waitingQueuePlayerUuidsMap = new HashMap<>();

    public static void init() {
        waitingQueues.add(new WaitingQueue("unranked_modern_sg", SurvivalGamesReborn.getInstance().getArenaManager().getArenas()));
    }

    public static void start() {
        for(WaitingQueue waitingQueue : waitingQueues) {
            waitingQueue.start();
        }
    }

    public static void joinQueue(Player player, String id) {
        final WaitingQueue waitingQueue = waitingQueues.stream().filter(e -> e.getId().equals(id)).toList().getFirst();
        waitingQueue.joinPlayer(player);
        waitingQueuePlayerUuidsMap.put(player.getUniqueId(), id);
    }

    public static void leaveQueue(Player player) {
        final WaitingQueue waitingQueue = waitingQueues.stream().filter(e -> e.getId().equals(waitingQueuePlayerUuidsMap.get(player.getUniqueId()))).toList().getFirst();
        waitingQueue.quitPlayer(player);
    }
}
