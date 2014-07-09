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

package colormatch.utils;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Chunk;
import net.minecraft.server.v1_7_R4.ChunkSection;

import org.bukkit.craftbukkit.v1_7_R4.CraftChunk;

public class SetBlockFast {

	@SuppressWarnings("deprecation")
	public static void set(org.bukkit.block.Block block, int id, byte data) {
		try {
			Chunk c = ((CraftChunk) block.getChunk()).getHandle();
			ChunkSection cs = c.getSections()[block.getY() >> 4];
			if (cs == null) {
				cs = new ChunkSection(block.getY() >> 4 << 4, !c.world.worldProvider.g);
				c.getSections()[block.getY() >> 4] = cs;
			}
			cs.setTypeId(block.getX() & 0xF, block.getY() & 0xF, block.getZ() & 0xF, Block.getById(id));
			cs.setData(block.getX() & 0xF, block.getY() & 0xF, block.getZ() & 0xF, data);
			c.world.notify(block.getX(), block.getY(), block.getZ());
		} catch (Throwable t) {
			block.setTypeIdAndData(id, data, false);
		}
	}

}
