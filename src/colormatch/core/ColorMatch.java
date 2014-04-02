package colormatch.core;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import colormatch.arena.Arena;
import colormatch.datahandler.ArenasManager;
import colormatch.datahandler.PlayerDataStore;

public class ColorMatch extends JavaPlugin {

	public PlayerDataStore pdata;
	public ArenasManager amanager;

	@Override
	public void onEnable() {
		pdata = new PlayerDataStore();
		amanager = new ArenasManager();
		final File arenasfolder = new File(this.getDataFolder() + File.separator + "arenas");
		arenasfolder.mkdirs();
		final ColorMatch instance = this;
		this.getServer().getScheduler().scheduleSyncDelayedTask(
			this,
			new Runnable() {
				@Override
				public void run() {
					for (String file : arenasfolder.list()) {
						Arena arena = new Arena(file.substring(0, file.length() - 4), instance);
						arena.getStructureManager().loadFromConfig();
						arena.getStatusManager().enableArena();
					};
				}
			},
			20
		);
	}

	@Override
	public void onDisable() {
		for (Arena arena : amanager.getArenas()) {
			arena.getStatusManager().disableArena();
			arena.getStructureManager().saveToConfig();
		}
		amanager = null;
		pdata = null;
	}

}
