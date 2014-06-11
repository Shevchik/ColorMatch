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

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import colormatch.util.SetBlockFast;

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
		regen();
	}

	@SuppressWarnings("deprecation")
	public void removeAllWoolExceptColor(DyeColor color) {
		int id = Material.AIR.getId();
		byte cd = color.getData();
		int y = p1.getBlockY();
		World world = getWorld();
		for (int x = p1.getBlockX() + 1; x < p2.getBlockX(); x++) {
			for (int z = p1.getBlockZ() + 1; z < p2.getBlockZ(); z++) {
				if (world.getBlockAt(x, y, z).getData() != cd) {
					SetBlockFast.setBlock(world, x, y, z, id, (byte) 0);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private final int WOOL_ID = Material.WOOL.getId();
	private final byte[] randomColorsArray = new byte[7100]; {
		byte[] COLORS = new byte[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		Random rnd = new Random();
		for (int i = 0; i < randomColorsArray.length; i++) {
			randomColorsArray[i] = COLORS[rnd.nextInt(COLORS.length)];
		}
	}
	private int randomCounter = 0;
	public void regen() {
		int y = p1.getBlockY();
		World world = getWorld();
		for (int x = p1.getBlockX() + 1; x < p2.getBlockX(); x++) {
			for (int z = p1.getBlockZ() + 1; z < p2.getBlockZ(); z++) {
				randomCounter++;
				if (randomCounter >= randomColorsArray.length) {
					randomCounter = 0;
				}
				SetBlockFast.setBlock(world, x, y, z, WOOL_ID, randomColorsArray[randomCounter]);
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
