package me.centralis.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.centralis.hns.game.GameManager;
import me.centy.uhc.utils.Util;

public class Death implements Listener {

	Util u = Util.getInstance();
	GameManager gm = GameManager.getInstance();

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		UUID playerUUID = player.getUniqueId();

		event.setDeathMessage("");

		if (gm.isHider(playerUUID)) {
			gm.removeHider(playerUUID);
			gm.addSeeker(playerUUID);
		}

		for (UUID playerUUID1 : gm.getAllPlayers()) {
			u.updateHidersAndSeekers(Bukkit.getPlayer(playerUUID1));
		}
		gm.checkIfSeekersWon();
	}

}