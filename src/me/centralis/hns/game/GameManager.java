package me.centralis.hns.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.centy.uhc.utils.Util;

public class GameManager {

	static Util u = Util.getInstance();

	// Singleton of GameManager class
	private static GameManager single_instance = null;

	public static GameManager getInstance() {
		if (single_instance == null) {
			single_instance = new GameManager();
		}
		return single_instance;
	}

	private ArrayList<UUID> players = new ArrayList<UUID>();
	private ArrayList<UUID> spectators = new ArrayList<UUID>();
	private ArrayList<UUID> allPlayers = new ArrayList<UUID>();

	private ArrayList<UUID> hiders = new ArrayList<UUID>();
	private ArrayList<UUID> seekers = new ArrayList<UUID>();

	public static Map<UUID, Material> hiderBlocks = new HashMap<UUID, Material>();
	public static Map<UUID, Location> previousLocation = new HashMap<UUID, Location>();

	Location loc = new Location(Bukkit.getWorld("world"), -242, 47, -615);

	// START Players

	public ArrayList<UUID> getPlayers() {
		return players;
	}

	public void addPlayer(UUID playerUUID) {
		players.add(playerUUID);
	}

	public void removePlayer(UUID playerUUID) {
		players.remove(playerUUID);
	}

	public boolean isPlayer(UUID playerUUID) {
		return players.contains(playerUUID);
	}

	// END

	// START Spectators

	public ArrayList<UUID> getSpectators() {
		return spectators;
	}

	public void addSpectator(UUID playerUUID) {
		spectators.add(playerUUID);
	}

	public void removeSpectator(UUID playerUUID) {
		spectators.remove(playerUUID);
	}

	public boolean isSpectator(UUID playerUUID) {
		return spectators.contains(playerUUID);
	}

	// END

	// START Players

	public ArrayList<UUID> getAllPlayers() {
		return allPlayers;
	}

	public void addAllPlayer(UUID playerUUID) {
		allPlayers.add(playerUUID);
	}

	public void removeAllPlayer(UUID playerUUID) {
		allPlayers.remove(playerUUID);
	}

	// END

	// START Hiders

	public ArrayList<UUID> getHiders() {
		return hiders;
	}

	public void addHider(UUID playerUUID) {
		hiders.add(playerUUID);
	}

	public void removeHider(UUID playerUUID) {
		hiders.remove(playerUUID);
	}

	public boolean isHider(UUID playerUUID) {
		return hiders.contains(playerUUID);
	}

	// END

	// START Seekers

	public ArrayList<UUID> getSeekers() {
		return seekers;
	}

	public void addSeeker(UUID playerUUID) {
		seekers.add(playerUUID);
	}

	public void removeSeeker(UUID playerUUID) {
		seekers.remove(playerUUID);
	}

	public boolean isSeeker(UUID playerUUID) {
		return seekers.contains(playerUUID);
	}

	// END

	// START Game Controls

	public void startGame() {
		GameState.setState(GameState.InGame);
		int seeker = getRandomInt(0, players.size());
		for (int i = 0; i < players.size(); i++) {
			if (i == seeker) {
				addSeeker(players.get(i));
				giveSeekerInventory(players.get(i));
				Bukkit.getPlayer(players.get(i))
						.sendMessage(u.messageFormat("[HNS] You have been chosen as the seeker.", "a"));
			} else {
				addHider(players.get(i));
				giveHiderInventory(players.get(i));
				Bukkit.getPlayer(players.get(i))
						.sendMessage(u.messageFormat("[HNS] You have been chosen as a hider.", "a"));
			}
		}
		for (UUID playerUUID : allPlayers) {
			u.createInGameScoreboard(Bukkit.getPlayer(playerUUID));
		}
		for (UUID playerUUID : hiders) {
			Bukkit.getPlayer(playerUUID).teleport(loc);
		}
	}

	public void checkIfSeekersWon() {
		if (getHiders().size() == 0) {
			GameState.setState(GameState.End);
			for (UUID playerUUID : getAllPlayers()) {
				Bukkit.getPlayer(playerUUID).sendMessage(u.messageFormat("[HNS] The seekers have won the game!", "a"));
			}
		}
	}

	public void checkIfHidersWonFromQuit() {
		if (getSeekers().size() == 0) {
			GameState.setState(GameState.End);
			for (UUID playerUUID : getAllPlayers()) {
				Bukkit.getPlayer(playerUUID).sendMessage(u.messageFormat("[HNS] The hiders have won the game!", "a"));
			}
		}
	}

	// END

	public int getRandomInt(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}

	public Location getLocation() {
		return loc;
	}

	// START Inventories

	public void giveHiderInventory(UUID playerUUID) {
		ItemStack woodSword = new ItemStack(Material.WOOD_SWORD);
		Bukkit.getPlayer(playerUUID).getInventory().addItem(woodSword);
	}

	public void giveSeekerInventory(UUID playerUUID) {
		ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
		ItemStack[] seekerArmor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS),
				new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.LEATHER_HELMET) };
		Bukkit.getPlayer(playerUUID).getInventory().addItem(stoneSword);
		Bukkit.getPlayer(playerUUID).getInventory().setArmorContents(seekerArmor);
	}

	// END

}