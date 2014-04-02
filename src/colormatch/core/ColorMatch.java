/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package colormatch.core;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import colormatch.arena.Arena;
import colormatch.commands.game.GameCommands;
import colormatch.datahandler.ArenasManager;
import colormatch.datahandler.PlayerDataStore;
import colormatch.eventhandler.PlayerLeaveArenaChecker;
import colormatch.eventhandler.PlayerStatusHandler;
import colormatch.eventhandler.RestrictionHandler;
import colormatch.eventhandler.WorldUnloadHandler;

public class ColorMatch extends JavaPlugin {

	public PlayerDataStore pdata;
	public ArenasManager amanager;

	@Override
	public void onEnable() {
		pdata = new PlayerDataStore();
		amanager = new ArenasManager();
		getCommand("colormatch").setExecutor(new GameCommands(this));
		getServer().getPluginManager().registerEvents(new PlayerStatusHandler(this), this);
		getServer().getPluginManager().registerEvents(new RestrictionHandler(this), this);
		getServer().getPluginManager().registerEvents(new PlayerLeaveArenaChecker(this), this);
		getServer().getPluginManager().registerEvents(new WorldUnloadHandler(this), this);
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
