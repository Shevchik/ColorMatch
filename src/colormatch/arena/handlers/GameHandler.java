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

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;

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

	public void startArena() {
		arena.getStatusManager().setRunning(true);
		for (Player player : arena.getPlayersManager().getPlayersInArena()) {
			player.sendMessage("Game started");
		}
		startRound();
	}

	private int roundtime = 10;
	private Random rnd = new Random();
	private DyeColor[] colors = DyeColor.values();
	private DyeColor currentcolor;
	@SuppressWarnings("deprecation")
	private void startRound() {
		if (roundtime <= 0) {
			for (Player player : arena.getPlayersManager().getPlayersInArena()) {
				arena.getPlayerHandler().leavePlayer(player, "Time out", "");
			}
			stopArena();
		}
		currentcolor = colors[rnd.nextInt(colors.length)];
		for (Player player : arena.getPlayersManager().getPlayersInArena()) {
			player.getInventory().setItem(0, new Wool(currentcolor).toItemStack());
			player.updateInventory();
			player.sendMessage("You have "+roundtime+" seconds to pick the safe position");
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(arena.plugin,
			new Runnable() {
				@Override
				public void run() {
					arena.getStructureManager().getGameLevel().removeAllWoolExceptColor(arena, currentcolor);
					Bukkit.getScheduler().scheduleSyncDelayedTask(arena.plugin, new 
						Runnable() {
							@Override
							public void run() {
								for (Player player : arena.getPlayersManager().getPlayersInArena()) {
									if (arena.getStructureManager().getGameLevel().getSpawnPoint().getBlockY() - player.getLocation().getBlockY() > 2) {
										arena.getPlayerHandler().leavePlayer(player, "You fell", player.getName() + " fell");
									}
								}
								if (arena.getPlayersManager().getPlayersCount() == 1) {
									Player player = arena.getPlayersManager().getPlayersInArena().iterator().next();
									arena.getPlayerHandler().leaveWinner(player, "You won the arena");
									broadcastWin(player);
									stopArena();
									return;
								}
								if (arena.getPlayersManager().getPlayersCount() == 0) {
									stopArena();
									return;
								}
								roundtime -= 1;
								arena.getStructureManager().getGameLevel().regen(arena);
								Bukkit.getScheduler().scheduleSyncDelayedTask(arena.plugin, 
									new Runnable() {
										@Override
										public void run() {
											startRound();
										}
									}
								);
							}
						}, 60
					);
				}
			}, roundtime*20
		);
	}

	public void stopArena() {
		arena.getStatusManager().setRunning(false);
		if (arena.getStatusManager().isArenaEnabled()) {
			arena.getStructureManager().getGameLevel().regen(arena);
		}
		roundtime = 10;
	}

	private void broadcastWin(Player player) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&9[ColorShuffleMatch] &a"+player.getName()+"&r won the game on arena &c"+arena.getArenaName()));
	}

}
