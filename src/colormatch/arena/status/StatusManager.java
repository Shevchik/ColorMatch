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

package colormatch.arena.status;

import org.bukkit.entity.Player;

import colormatch.arena.Arena;

public class StatusManager {

	private Arena arena;
	public StatusManager(Arena arena) {
		this.arena = arena;
	}

	private boolean enabled = false;
	private boolean starting = false;
	private boolean running = false;

	public boolean isArenaEnabled() {
		return enabled;
	}

	public boolean enableArena() {
		if (arena.getStructureManager().isArenaConfigured()) {
			enabled = true;
			return true;
		}
		return false;
	}

	public void disableArena() {
		enabled = false;
		for (Player player : arena.getPlayersManager().getPlayersInArena()) {
			arena.getPlayerHandler().leavePlayer(player, "Arena is disabling", "");
		}
		if (isArenaStarting()) {
			arena.getGameHandler().stopArenaCountdown();
		}
		if (isArenaRunning()) {
			arena.getGameHandler().stopArena();
		}
		if (arena.getStructureManager().getGameLevel().getSpawnPoint() != null) {
			arena.getStructureManager().getGameLevel().regenNow();
		}
	}

	public boolean isArenaStarting() {
		return starting;
	}

	public void setStarting(boolean starting) {
		this.starting = starting;
	}

	public boolean isArenaRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
