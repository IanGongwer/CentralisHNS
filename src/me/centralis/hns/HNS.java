package me.centralis.hns;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.centralis.hns.game.GameState;
import me.centralis.hns.runnables.EndRunnable;
import me.centralis.hns.runnables.GameRunnable;
import me.centralis.listeners.AntiDrop;
import me.centralis.listeners.Break;
import me.centralis.listeners.Death;
import me.centralis.listeners.EntitySpawning;
import me.centralis.listeners.Fall;
import me.centralis.listeners.Hunger;
import me.centralis.listeners.Join;
import me.centralis.listeners.Leave;
import me.centralis.listeners.PvP;
import me.centralis.listeners.Respawn;
import me.centralis.listeners.Weather;

public class HNS extends JavaPlugin {

	private static HNS instance;

	@SuppressWarnings("deprecation")
	public void registerRunnables() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new GameRunnable(), 0L, 20L);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new EndRunnable(), 0L, 20L);
	}

	public void registerListeners() {
		getServer().getPluginManager().registerEvents(new AntiDrop(), this);
		getServer().getPluginManager().registerEvents(new Break(), this);
		getServer().getPluginManager().registerEvents(new Death(), this);
		getServer().getPluginManager().registerEvents(new EntitySpawning(), this);
		getServer().getPluginManager().registerEvents(new Fall(), this);
		getServer().getPluginManager().registerEvents(new Hunger(), this);
		getServer().getPluginManager().registerEvents(new Join(), this);
		getServer().getPluginManager().registerEvents(new Leave(), this);
		getServer().getPluginManager().registerEvents(new PvP(), this);
		getServer().getPluginManager().registerEvents(new Respawn(), this);
		getServer().getPluginManager().registerEvents(new Weather(), this);
	}

	public void registerCommands() {

	}

	public void onEnable() {
		instance = this;
		registerRunnables();
		registerListeners();
		registerCommands();
		GameState.setState(GameState.Lobby);
		Bukkit.getWorld("world").setGameRuleValue("doDaylightCycle", "false");
	}

	public void onDisable() {

	}

	public static HNS getInstance() {
		return instance;
	}

}