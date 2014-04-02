package colormatch.arena.structure;

import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import colormatch.arena.Arena;

public class StructureManager {

	private Arena arena;
	public StructureManager(Arena arena) {
		this.arena = arena;
	}

	private GameLevel gl = new GameLevel();
	public GameLevel getGameLevel() {
		return gl;
	}

	private int minPlayers = 2;
	public int getMinPlayers() {
		return minPlayers;
	}

	private int maxPlayers = 6;
	public int getMaxPlayers() {
		return maxPlayers;
	}

	public String isArenaConfiguredString() {
		if (gl.getSpawnPoint() == null) {
			return "Arena gamelevel not set";
		}
		return "yes";
	}
	public boolean isArenaConfigured() {
		return isArenaConfiguredString().equals("yes");
	}

	public void saveToConfig() {
		FileConfiguration config = new YamlConfiguration();
		config.set("minplayers", minPlayers);
		config.set("maxplayers", maxPlayers);
		gl.saveToConfig(config);
		try {
			config.save(arena.getArenaFile());
		} catch (IOException e) {
		}
	}

	public void loadFromConfig() {
		FileConfiguration config = YamlConfiguration.loadConfiguration(arena.getArenaFile());
		minPlayers = config.getInt("minplayers", minPlayers);
		maxPlayers = config.getInt("minplayers", minPlayers);
		gl.loadFromConfig(config);
	}

}
