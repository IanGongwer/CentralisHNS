package me.centralis.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import me.centy.uhc.utils.Util;

public class EntitySpawning implements Listener {

	Util u = Util.getInstance();

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		event.setCancelled(true);
	}

}