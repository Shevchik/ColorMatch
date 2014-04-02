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
