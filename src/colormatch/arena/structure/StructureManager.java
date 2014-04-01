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
	
	private Vector spawnpoint = null;
	public Vector getSpawnPointVector() {
		return spawnpoint;
	}
	public Location getSpawnPoint() {
		Location spawn = new Location(getWorld(), spawnpoint.getX(), spawnpoint.getY(), spawnpoint.getZ());
		return spawn;
	}

}
