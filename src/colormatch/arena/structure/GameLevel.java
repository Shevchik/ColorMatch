package colormatch.arena.structure;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

public class GameLevel {

	private Vector centralPoint;
	public Vector getCentralPoint() {
		return centralPoint;
	}
	
	public void saveToConfig(FileConfiguration config) {
		config.set("centralpoint", centralPoint);
	}
	
	public void loadFromConfig(FileConfiguration config) {
		centralPoint = config.getVector("centralpoint");
	}

}
