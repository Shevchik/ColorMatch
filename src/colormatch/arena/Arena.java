package colormatch.arena;

import java.io.File;

import colormatch.arena.status.PlayersManager;
import colormatch.arena.status.StatusManager;
import colormatch.arena.structure.StructureManager;
import colormatch.core.ColorMatch;

public class Arena {

	public ColorMatch plugin;

	public Arena(String name, ColorMatch plugin) {
		this.name = name;
		this.arenafile = new File(plugin.getDataFolder(), name+".yml");
		this.plugin = plugin;
	}

	private String name;
	public String getArenaName() {
		return name;
	}

	private File arenafile;
	public File getArenaFile() {
		return arenafile;
	}

	private StatusManager statusManager = new StatusManager(this);
	public StatusManager getStatusManager() {
		return statusManager;
	}

	private PlayersManager playersManager = new PlayersManager();
	public PlayersManager getPlayersManager() {
		return playersManager;
	}

	private StructureManager structureManager = new StructureManager(this);
	public StructureManager getStructureManager() {
		return structureManager;
	}

}