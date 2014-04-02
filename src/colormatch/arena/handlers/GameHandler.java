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

package colormatch.arena.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import colormatch.arena.Arena;

public class GameHandler {

	private Arena arena;

	public GameHandler(Arena arena) {
		this.arena = arena;
	}

	private int runtaskid;
	private int count = 10;

	public void runArenaCountdown() {
		arena.getStatusManager().setStarting(true);
		runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(
			arena.plugin,
			new Runnable() {
				@Override
				public void run() {
					if (arena.getPlayersManager().getPlayersCount() < arena.getStructureManager().getMinPlayers()) {
						stopArenaCountdown();
					} else if (count == 0) {
						stopArenaCountdown();
						startArena();
					} else {
						for (Player player : arena.getPlayersManager().getPlayersInArena()) {
							player.sendMessage("Areans starts in: "+count);
						}
						count--;
					}
				}
			},
			0, 20
		);
	}

	public void stopArenaCountdown() {
		arena.getStatusManager().setStarting(false);
		count = 10;
		Bukkit.getScheduler().cancelTask(runtaskid);
	}


	private int timelimit;
	private int arenahandler;

	public void startArena() {
		arena.getStatusManager().setRunning(true);
		for (Player player : arena.getPlayersManager().getPlayersInArena()) {
			player.sendMessage("Game started");
		}
	}

	public void stopArena() {
		arena.getStatusManager().setRunning(false);
		Bukkit.getScheduler().cancelTask(arenahandler);
		if (arena.getStatusManager().isArenaEnabled()) {
			arena.getStructureManager().getGameLevel().regen();
		}
	}

	private void broadcastWin(Player player) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&9[ColorShuffleMatch] &a"+player.getName()+"&r won the game on arena &c"+arena.getArenaName()));
	}

}
