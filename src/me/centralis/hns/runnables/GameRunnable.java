package me.centralis.hns.runnables;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.centralis.hns.game.GameManager;
import me.centralis.hns.game.GameState;
import me.centy.uhc.utils.Util;

public class GameRunnable extends BukkitRunnable {

	Util u = Util.getInstance();
	GameManager gm = GameManager.getInstance();

	private static int totalTime = 0;

	@Override
	public void run() {
		if (GameState.isInGame()) {
			totalTime++;
			for (UUID playerUUID : gm.getPlayers()) {
				if (u.hasScoreboard(Bukkit.getPlayer(playerUUID))) {
					u.updateTime(Bukkit.getPlayer(playerUUID));
				}
			}
			if (getFormattedTime().equalsIgnoreCase("0:15")) {
				Bukkit.getPlayer(gm.getSeekers().get(0)).teleport(gm.getLocation());
				for (UUID playerUUID : gm.getPlayers()) {
					Bukkit.getPlayer(playerUUID).sendMessage(u.messageFormat("[HNS] The seeker is now seeking!", "a"));
				}
			}
			if (getFormattedTime().equalsIgnoreCase("1:00")) {
				for (UUID playerUUID : gm.getAllPlayers()) {
					Bukkit.getPlayer(playerUUID).sendMessage(u.messageFormat("[HNS] 4 minutes remaining!", "a"));
				}
			}
			if (getFormattedTime().equalsIgnoreCase("2:00")) {
				for (UUID playerUUID : gm.getAllPlayers()) {
					Bukkit.getPlayer(playerUUID).sendMessage(u.messageFormat("[HNS] 3 minutes remaining!", "a"));
				}
			}
			if (getFormattedTime().equalsIgnoreCase("3:00")) {
				for (UUID playerUUID : gm.getAllPlayers()) {
					Bukkit.getPlayer(playerUUID).sendMessage(u.messageFormat("[HNS] 2 minutes remaining!", "a"));
				}
			}
			if (getFormattedTime().equalsIgnoreCase("4:00")) {
				for (UUID playerUUID : gm.getAllPlayers()) {
					Bukkit.getPlayer(playerUUID).sendMessage(u.messageFormat("[HNS] 1 minute remaining!", "a"));
				}
			}
			if (getFormattedTime().equalsIgnoreCase("5:00")) {
				if (gm.getHiders().size() != 0) {
					GameState.setState(GameState.End);
					for (UUID playerUUID : gm.getAllPlayers()) {
						Bukkit.getPlayer(playerUUID)
								.sendMessage(u.messageFormat("[HNS] The hiders have won the game!", "a"));
					}
				}
			}
		}
	}

	public static String getFormattedTime() {
		int second = totalTime % 60;
		int minute = totalTime / 60;
		if (minute >= 60) {
			int hour = minute / 60;
			minute %= 60;
			return hour + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
		}
		return minute + ":" + (second < 10 ? "0" + second : second);
	}

}