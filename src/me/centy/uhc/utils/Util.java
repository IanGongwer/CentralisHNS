package me.centy.uhc.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import me.centralis.hns.game.GameManager;
import me.centralis.hns.runnables.GameRunnable;

public class Util {

	static GameManager gm = GameManager.getInstance();

	// Singleton of Util class
	private static Util single_instance = null;

	public static Util getInstance() {
		if (single_instance == null) {
			single_instance = new Util();
		}
		return single_instance;
	}

	public String messageFormat(String message, String color) {
		return ChatColor.translateAlternateColorCodes('&', '&' + color + message);
	}

	// START Scoreboard

	public void createLobbyScoreboard(Player player) {
		BPlayerBoard board = Netherboard.instance().createBoard(player, "Hide N' Seek");
		board.set("Waiting for players...", 15);
		board.set("", 14);
		board.set("Players: " + gm.getPlayers().size(), 13);
	}

	public void createInGameScoreboard(Player player) {
		BPlayerBoard board = Netherboard.instance().createBoard(player, "Hide N' Seek");
		board.set("Game Time: " + GameRunnable.getFormattedTime(), 15);
		board.set(" ", 14);
		board.set("Hiders: " + gm.getHiders().size(), 13);
		board.set("", 12);
		board.set("Seekers: " + gm.getSeekers().size(), 11);
	}

	// END

	public boolean hasScoreboard(Player player) {
		BPlayerBoard board = Netherboard.instance().getBoard(player);
		if (board == null) {
			return false;
		} else {
			return true;
		}
	}

	public BPlayerBoard getScoreboard(Player player) {
		BPlayerBoard board = Netherboard.instance().getBoard(player);
		return board;
	}

	// START Update Lines

	public void updateTime(Player player) {
		BPlayerBoard board = getScoreboard(player);
		board.set("Game Time: " + GameRunnable.getFormattedTime(), 15);
	}

	public void updatePlayers(Player player) {
		BPlayerBoard board = getScoreboard(player);
		board.set("Players: " + gm.getPlayers().size(), 13);
	}

	public void updateHiders(Player player) {
		BPlayerBoard board = getScoreboard(player);
		board.set("Hiders: " + gm.getHiders().size(), 13);
	}

	public void updateSeekers(Player player) {
		BPlayerBoard board = getScoreboard(player);
		board.set("Seekers: " + gm.getSeekers().size(), 11);
	}

	public void updateHidersAndSeekers(Player player) {
		updateHiders(player);
		updateSeekers(player);
	}

	// END

}