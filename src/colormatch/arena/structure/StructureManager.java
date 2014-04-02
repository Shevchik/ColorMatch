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
