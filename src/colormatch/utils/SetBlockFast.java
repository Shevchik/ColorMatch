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

import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Chunk;
import net.minecraft.server.v1_8_R1.ChunkSection;

import org.bukkit.craftbukkit.v1_8_R1.CraftChunk;

public class SetBlockFast {

	@SuppressWarnings("deprecation")
	public static void set(org.bukkit.block.Block block, int id, byte data) {
		try {
			Chunk chunk = ((CraftChunk) block.getChunk()).getHandle();
			ChunkSection section = chunk.getSections()[block.getY() >> 4];
			if (section == null) {
				section = new ChunkSection(block.getY() >> 4 << 4, !chunk.world.worldProvider.o());
				chunk.getSections()[block.getY() >> 4] = section;
			}
			section.setType(block.getX() & 0xF, block.getY() & 0xF, block.getZ() & 0xF, Block.getByCombinedId(data << 12 | id));
			chunk.world.notify(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		} catch (Throwable t) {
			block.setTypeIdAndData(id, data, false);
		}
	}

}
