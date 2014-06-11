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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import colormatch.arena.Arena;

public class GameLevel {

	private Vector p1;
	private Vector p2;
	private Vector centralPoint;
	public Vector getSpawnPoint() {
		return centralPoint;
	}
	private String world;
	public World getWorld() {
		return Bukkit.getWorld(world);
	}

	public void setGameLevel(Location location) {
		world = location.getWorld().getName();
		p1 = location.toVector();
		p2 = p1.clone().add(new Vector(32, 0, 32));
		centralPoint = p1.clone().add(new Vector(16, 1, 16));
		regenNow();
	}

	private final int MAX_BLOCKS_PER_TICK = 100;

	@SuppressWarnings("deprecation")
	public void removeAllWoolExceptColor(final Arena arena, DyeColor color) {
		int y = p1.getBlockY();
		World world = getWorld();
		LinkedList<Block> blocks = new LinkedList<Block>();
		for (int x = p1.getBlockX() + 1; x < p2.getBlockX(); x++) {
			for (int z = p1.getBlockZ() + 1; z < p2.getBlockZ(); z++) {
				Block b = world.getBlockAt(x, y, z);
				if (b.getData() != color.getData()) {
					blocks.add(b);
				}
			}
		}
		final Iterator<Block> it = blocks.iterator();
		for (int delay = 1; delay <= (blocks.size() / MAX_BLOCKS_PER_TICK ) + 1; delay++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(
				arena.plugin,
				new RemoveWool(arena, it),
				delay
			);
		}
	}

	private class RemoveWool implements Runnable {

		private Arena arena;
		private Iterator<Block> it;
		public RemoveWool(Arena arena, Iterator<Block> it) {
			this.arena = arena;
			this.it = it;
		}

		@Override
		public void run() {
			if (arena.getStatusManager().isArenaEnabled()) {
				int curblocks = 0;
				while (it.hasNext() && curblocks < MAX_BLOCKS_PER_TICK) {
					it.next().setType(Material.AIR);
					curblocks++;
				}
			}
		}
	
	}

	@SuppressWarnings("deprecation")
	private final int WOOL_ID = Material.WOOL.getId();
	private final byte[] COLORS = new byte[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
	private final byte[] randomColorsArray = new byte[7100]; {
		Random rnd = new Random();
		for (int i = 0; i < randomColorsArray.length; i++) {
			randomColorsArray[i] = COLORS[rnd.nextInt(COLORS.length)];
		}
	}
	private int randomCounter = 0;
	@SuppressWarnings("deprecation")
	public void regenNow() {
		int y = p1.getBlockY();
		World world = getWorld();
		for (int x = p1.getBlockX() + 1; x < p2.getBlockX(); x++) {
			for (int z = p1.getBlockZ() + 1; z < p2.getBlockZ(); z++) {
				randomCounter++;
				if (randomCounter >= randomColorsArray.length) {
					randomCounter = 0;
				}
				world.getBlockAt(x, y, z).setTypeIdAndData(WOOL_ID, randomColorsArray[randomCounter], false);
			}
		}
	}
	public void regen(final Arena arena) {
		int y = p1.getBlockY();
		World world = getWorld();
		LinkedList<Block> blocks = new LinkedList<Block>();
		for (int x = p1.getBlockX() + 1; x < p2.getBlockX(); x++) {
			for (int z = p1.getBlockZ() + 1; z < p2.getBlockZ(); z++) {
				blocks.add(world.getBlockAt(x, y, z));
			}
		}
		final Iterator<Block> it = blocks.iterator();
		for (int delay = 1; delay <= (blocks.size() / MAX_BLOCKS_PER_TICK ) + 1; delay++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(
				arena.plugin,
				new RegenLevel(arena, it),
				delay
			);
		}
	}

	private class RegenLevel implements Runnable {

		private Arena arena;
		private Iterator<Block> it;
		public RegenLevel(Arena arena, Iterator<Block> it) {
			this.arena = arena;
			this.it = it;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			if (arena.getStatusManager().isArenaEnabled()) {
				int curblocks = 0;
				while (it.hasNext() && curblocks < MAX_BLOCKS_PER_TICK) {
					randomCounter++;
					if (randomCounter >= randomColorsArray.length) {
						randomCounter = 0;
					}
					it.next().setTypeIdAndData(WOOL_ID, randomColorsArray[randomCounter], false);
					curblocks++;
				}
			}
		}
	}

	public void saveToConfig(FileConfiguration config) {
		config.set("centralpoint", centralPoint);
		config.set("p1", p1);
		config.set("p2", p2);
		config.set("world", world);
	}

	public void loadFromConfig(FileConfiguration config) {
		centralPoint = config.getVector("centralpoint");
		p1 = config.getVector("p1");
		p2 = config.getVector("p2");
		world = config.getString("world");
	}

}
