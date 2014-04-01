package colormatch.core;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import colormatch.arena.Arena;

public class ColorMatch extends JavaPlugin {
	
	@Override
	public void onEnable() {
		// load arenas
		final File arenasfolder = new File(this.getDataFolder() + File.separator + "arenas");
		arenasfolder.mkdirs();
		final ColorMatch instance = this;
		this.getServer().getScheduler().scheduleSyncDelayedTask(
			this,
			new Runnable() {
				@Override
				public void run() {
					// load arenas
					for (String file : arenasfolder.list()) {
						Arena arena = new Arena(file.substring(0, file.length() - 4), instance);
						arena.getStatusManager().enableArena();
					};
				}
			},
			20
		);
	}
	
	@Override
	public void onDisable() {
		
	}

}
