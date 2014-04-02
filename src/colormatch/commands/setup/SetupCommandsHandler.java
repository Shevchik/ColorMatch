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

package colormatch.commands.setup;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import colormatch.core.ColorMatch;

public class SetupCommandsHandler implements CommandExecutor {

	private HashMap<String, CommandHandlerInterface> commandHandlers = new HashMap<String, CommandHandlerInterface>();

	public SetupCommandsHandler(ColorMatch plugin) {
		//commandHandlers.put("deletelobby", new DeleteArena(plugin));
		//commandHandlers.put("create", new CreateArena(plugin));
		//commandHandlers.put("delete", new DeleteArena(plugin));
		//commandHandlers.put("setgamelevel", new SetGameLevel(plugin, plselection));
		//commandHandlers.put("setmaxplayers", new SetMaxPlayers(plugin));
		//commandHandlers.put("setminplayers", new SetMinPlayers(plugin));
		//commandHandlers.put("finish", new FinishArena(plugin));
		//commandHandlers.put("disable", new DisableArena(plugin));
		//commandHandlers.put("enable", new EnableArena(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Player is expected");
			return true;
		}
		Player player = (Player) sender;
		if (!player.hasPermission("colormatch.setup")) {
			player.sendMessage(ChatColor.RED+"You don't have permissions to do that");
			return true;
		}
		if (args.length > 0 && commandHandlers.containsKey(args[0])) {
			CommandHandlerInterface commandh = commandHandlers.get(args[0]);
			boolean result = commandh.handleCommand(player, Arrays.copyOfRange(args, 1, args.length));
			return result;
		}
		return false;
	}

}
