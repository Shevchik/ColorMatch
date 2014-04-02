package colormatch.arena.structure;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class StructureManager {

	private String world;
	public World getWorld() {
		return Bukkit.getWorld(world);
	}

	private GameLevel gl = new GameLevel();
	public GameLevel getGameLevel() {
		return gl;
	}

}
