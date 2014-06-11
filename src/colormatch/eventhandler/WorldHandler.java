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

package colormatch.eventhandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import colormatch.arena.Arena;
import colormatch.core.ColorMatch;

public class WorldHandler implements Listener {

	private ColorMatch plugin;

	public WorldHandler(ColorMatch plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWorldUnload(WorldUnloadEvent e) {
		String worldname = e.getWorld().getName();
		for (Arena arena : plugin.amanager.getArenas()) {
			if (arena.getStructureManager().isArenaConfigured()) {
				if (arena.getStructureManager().getGameLevel().getWorld().getName().equals(worldname)) {
					arena.getStatusManager().disableArena();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWorldLoad(WorldLoadEvent e) {
		String worldname = e.getWorld().getName();
		for (Arena arena : plugin.amanager.getArenas()) {
			if (arena.getStructureManager().isArenaConfigured()) {
				if (arena.getStructureManager().getGameLevel().getWorld().getName().equals(worldname)) {
					arena.getStatusManager().enableArena();
				}
			}
		}
	}

}
