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

package colormatch.commands.game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import colormatch.arena.Arena;
import colormatch.core.ColorMatch;

public class GameCommands implements CommandExecutor {

	private ColorMatch plugin;

	public GameCommands(ColorMatch plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("A player is expected");
			return true;
		}
		Player player = (Player) sender;
		if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
			Arena arena = plugin.amanager.getArenaByName(args[1]);
			if (arena != null) {
				boolean canJoin = arena.getPlayerHandler().checkJoin(player);
				if (canJoin) {
					arena.getPlayerHandler().spawnPlayer(player, "You joined the arena", "Player "+player.getName()+" joined the arena");
				}
				return true;
			} else {
				sender.sendMessage("Arena does not exist");
				return true;
			}
		} else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
			Arena arena = plugin.amanager.getPlayerArena(player.getName());
			if (arena != null) {
				arena.getPlayerHandler().leavePlayer(player, "You left the arena", "Player "+player.getName()+" left the aren");
				return true;
			} else {
				sender.sendMessage("You are not in arena");
				return true;
			}
		}
		return false;
	}
	
}
