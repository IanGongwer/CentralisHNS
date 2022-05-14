package me.centralis.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.centralis.hns.game.GameState;
import me.centy.uhc.utils.Util;

public class Fall implements Listener {

	Util u = Util.getInstance();

	@EventHandler
	public void onPlayerFall(EntityDamageEvent event) {
		if (GameState.isInGame()) {
			event.setCancelled(false);
		} else {
			event.setCancelled(true);
		}
	}

}