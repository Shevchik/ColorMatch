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

package colormatch.arena;

import java.io.File;

import colormatch.arena.handlers.GameHandler;
import colormatch.arena.handlers.PlayerHandler;
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

	private PlayerHandler playerHandler = new PlayerHandler(this);
	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	private PlayersManager playersManager = new PlayersManager();
	public PlayersManager getPlayersManager() {
		return playersManager;
	}

	private StructureManager structureManager = new StructureManager(this);
	public StructureManager getStructureManager() {
		return structureManager;
	}

	private GameHandler gameHandler = new GameHandler(this);
	public GameHandler getGameHandler() {
		return gameHandler;
	}

}