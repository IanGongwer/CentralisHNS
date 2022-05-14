package me.centralis.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.centralis.hns.game.GameManager;
import me.centralis.hns.game.GameState;
import me.centy.uhc.utils.Util;

public class Join implements Listener {

	GameManager gm = GameManager.getInstance();
	Util u = Util.getInstance();

	Location loc = new Location(Bukkit.getWorld("world"), -273.5, 52, -649.5);

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UUID playerUUID = player.getUniqueId();

		event.setJoinMessage("");
		resetProcedure(player);
		lobbyProcedure(player);
		inGameJoinCheck(playerUUID);
		endJoinCheck(playerUUID);
	}

	public void resetProcedure(Player player) {
		gm.addAllPlayer(player.getUniqueId());
		player.setMaxHealth(30);
		player.setHealth(30);
		player.setFoodLevel(20);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);

		player.teleport(loc);
	}

	public void lobbyProcedure(Player player) {
		UUID playerUUID = player.getUniqueId();

		player.teleport(loc);

		if (GameState.isLobby()) {
			player.setGameMode(GameMode.SURVIVAL);
			gm.addPlayer(playerUUID);
			u.createLobbyScoreboard(player);

			for (UUID playerUUID1 : gm.getPlayers()) {
				u.updatePlayers(Bukkit.getPlayer(playerUUID1));
			}
			if (gm.getPlayers().size() == 2) {
				gm.startGame();
			}
		}
	}

	public void inGameJoinCheck(UUID playerUUID) {
		if (GameState.isInGame()) {
			if (!gm.isPlayer(playerUUID)) {
				u.createInGameScoreboard(Bukkit.getPlayer(playerUUID));
				gm.addSpectator(playerUUID);
				Bukkit.getPlayer(playerUUID).setGameMode(GameMode.SPECTATOR);
			}
		}
	}

	public void endJoinCheck(UUID playerUUID) {
		if (GameState.isEnd()) {
			u.createInGameScoreboard(Bukkit.getPlayer(playerUUID));
			gm.addSpectator(playerUUID);
			Bukkit.getPlayer(playerUUID).setGameMode(GameMode.SPECTATOR);
		}
	}

}