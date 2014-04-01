package colormatch.arena;

import colormatch.arena.status.PlayersManager;
import colormatch.arena.status.StatusManager;
import colormatch.core.ColorMatch;

public class Arena {

	public ColorMatch plugin;

	private String name;
	public Arena(String name, ColorMatch plugin) {
		this.name = name;
		this.plugin = plugin;
	}

	public String getArenaName() {
		return name;
	}

	private StatusManager statusManager = new StatusManager(this);
	public StatusManager getStatusManager() {
		return statusManager;
	}

	private PlayersManager playersManager = new PlayersManager();
	public PlayersManager getPlayersManager() {
		return playersManager;
	}
	
}