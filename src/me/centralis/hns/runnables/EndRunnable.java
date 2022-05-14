package me.centralis.hns.runnables;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.centralis.hns.game.GameManager;
import me.centralis.hns.game.GameState;

public class EndRunnable extends BukkitRunnable {

	private int uptimeSeconds = 0;

	GameManager gm = GameManager.getInstance();

	@Override
	public void run() {
		if (GameState.isEnd()) {
			uptimeSeconds++;
			if (uptimeSeconds == 10) {
				for (UUID playerUUID : gm.getAllPlayers()) {
					Bukkit.getPlayer(playerUUID).kickPlayer("Thank you for playing Hide N Seek");
				}
				Bukkit.getServer().shutdown();
			}
		}

	}

}