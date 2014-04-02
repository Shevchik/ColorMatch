package colormatch.arena.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import colormatch.arena.Arena;

public class GameHandler {

	private Arena arena;

	public GameHandler(Arena arena) {
		this.arena = arena;
	}

	private int runtaskid;
	private int count = 10;

	public void runArenaCountdown() {
		arena.getStatusManager().setStarting(true);
		runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(
			arena.plugin,
			new Runnable() {
				@Override
				public void run() {
					if (arena.getPlayersManager().getPlayersCount() < arena.getStructureManager().getMinPlayers()) {
						stopArenaCountdown();
					} else if (count == 0) {
						stopArenaCountdown();
						startArena();
					} else {
						for (Player player : arena.getPlayersManager().getPlayersInArena()) {
							player.sendMessage("Areans starts in: "+count);
						}
						count--;
					}
				}
			},
			0, 20
		);
	}

	public void stopArenaCountdown() {
		arena.getStatusManager().setStarting(false);
		count = 10;
		Bukkit.getScheduler().cancelTask(runtaskid);
	}


	private int timelimit;
	private int arenahandler;

	public void startArena() {
		arena.getStatusManager().setRunning(true);
		for (Player player : arena.getPlayersManager().getPlayersInArena()) {
			player.sendMessage("Game started");
		}
	}

	public void stopArena() {
		arena.getStatusManager().setRunning(false);
		Bukkit.getScheduler().cancelTask(arenahandler);
		if (arena.getStatusManager().isArenaEnabled()) {
			arena.getStructureManager().getGameLevel().regen();
		}
	}

	private void broadcastWin(Player player) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&9[ColorShuffleMatch] &a"+player.getName()+"&r won the game on arena &c"+arena.getArenaName()));
	}

}
