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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import colormatch.arena.Arena;
import colormatch.arena.bars.Bars;

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
							player.sendMessage(ChatColor.GOLD+"Игра начнётся через: "+count);
							Bars.setBar(player, Bars.starting, count, count, count * 10);
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
			player.sendMessage(ChatColor.GOLD+"Игра началась");
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
				arena.getPlayerHandler().leavePlayer(player, ChatColor.GOLD+"Время вышло", "");
			}
			stopArena();
		}
		currentcolor = colors[rnd.nextInt(colors.length)];
		for (Player player : arena.getPlayersManager().getPlayersInArena()) {
			player.getInventory().setItem(0, new ItemStack(Material.STAINED_CLAY, 1, currentcolor.getDyeData()));
			player.updateInventory();
			player.sendMessage(ChatColor.GOLD+"У вас есть "+roundtime+" секунд для нахождения безопасной позиции");
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(arena.plugin,
			new Runnable() {
				@Override
				public void run() {
					arena.getStructureManager().getGameLevel().removeAllWoolExceptColor(currentcolor);
					Bukkit.getScheduler().scheduleSyncDelayedTask(
						arena.plugin, new Runnable() {
							@Override
							public void run() {
								for (Player player : arena.getPlayersManager().getPlayersInArena()) {
									if (arena.getStructureManager().getGameLevel().getSpawnPoint().getBlockY() - player.getLocation().getBlockY() > 2) {
										arena.getPlayerHandler().leavePlayer(player, ChatColor.GOLD+"Вы упали", player.getName() + " упал");
									}
								}
								if (arena.getPlayersManager().getPlayersCount() == 1) {
									Player player = arena.getPlayersManager().getPlayersInArena().iterator().next();
									arena.getPlayerHandler().leaveWinner(player, ChatColor.GOLD+"Вы выиграли");
									broadcastWin(player);
									stopArena();
									return;
								}
								if (arena.getPlayersManager().getPlayersCount() == 0) {
									stopArena();
									return;
								}
								roundtime -= 1;
								arena.getStructureManager().getGameLevel().regen();
								for (Player player : arena.getPlayersManager().getPlayersInArena()) {
									Bars.setBar(player, Bars.playing, arena.getPlayersManager().getPlayersCount(), 0, 100);
								}
								startRound();
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
			arena.getStructureManager().getGameLevel().regen();
		}
		roundtime = 10;
	}

	private void broadcastWin(Player player) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&9[ColorShuffleMatch] &a"+player.getName()+"&r выиграл игру на арене &c"+arena.getArenaName()));
	}

}
