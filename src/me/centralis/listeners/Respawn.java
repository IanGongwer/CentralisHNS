package me.centralis.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.centralis.hns.game.GameManager;

public class Respawn implements Listener {

	GameManager gm = GameManager.getInstance();

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		UUID playerUUID = event.getPlayer().getUniqueId();
		event.setRespawnLocation(gm.getLocation());
		gm.giveSeekerInventory(playerUUID);
	}

}