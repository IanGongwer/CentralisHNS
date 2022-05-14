package me.centralis.listeners;

import java.util.UUID;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.centralis.hns.game.GameManager;
import me.centralis.hns.game.GameState;

public class PvP implements Listener {

	GameManager gm = GameManager.getInstance();

	@EventHandler
	public void onPlayerPvP(EntityDamageByEntityEvent event) {
		if (event.getEntityType().equals(EntityType.PLAYER)) {
			UUID damager = (UUID) event.getDamager().getUniqueId();
			UUID damagee = (UUID) event.getEntity().getUniqueId();
			if (!GameState.isInGame()) {
				event.setCancelled(true);
			} else {
				if (bothHiders(damager, damagee) || bothSeekers(damager, damagee)) {
					event.setCancelled(true);
				} else {
					event.setCancelled(false);
				}
			}
		}
	}

	public boolean bothHiders(UUID damager, UUID damagee) {
		if (gm.isHider(damager) && gm.isHider(damagee)) {
			return true;
		}
		return false;
	}

	public boolean bothSeekers(UUID damager, UUID damagee) {
		if (gm.isSeeker(damager) && gm.isSeeker(damagee)) {
			return true;
		}
		return false;
	}

}