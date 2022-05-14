package me.centralis.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.centralis.hns.game.GameManager;
import me.centralis.hns.game.GameState;
import me.centy.uhc.utils.Util;

public class Leave implements Listener {

	Util u = Util.getInstance();
	GameManager gm = GameManager.getInstance();

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		UUID playerUUID = player.getUniqueId();

		event.setQuitMessage("");

		if (GameState.isLobby()) {
			gm.removePlayer(playerUUID);
		}

		inGameLeaveCheck(playerUUID);
		endLeaveCheck(playerUUID);
	}

	public void inGameLeaveCheck(UUID playerUUID) {
		if (GameState.isInGame()) {
			if (gm.isPlayer(playerUUID)) {
				gm.removePlayer(playerUUID);
				gm.removeAllPlayer(playerUUID);
				if (gm.isHider(playerUUID)) {
					gm.removeHider(playerUUID);
				} else {
					gm.removeSeeker(playerUUID);
				}
				gm.checkIfSeekersWon();
				gm.checkIfHidersWonFromQuit();
			} else {
				gm.removeSpectator(playerUUID);
			}
			for (UUID playerUUID1 : gm.getAllPlayers()) {
				u.updateHidersAndSeekers(Bukkit.getPlayer(playerUUID1));
			}
		}
	}

	public void endLeaveCheck(UUID playerUUID) {
		if (GameState.isEnd()) {
			if (gm.isPlayer(playerUUID)) {
				gm.removePlayer(playerUUID);
			} else {
				gm.removeSpectator(playerUUID);
			}
		}
	}

}