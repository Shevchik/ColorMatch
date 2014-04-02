package colormatch.arena.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import colormatch.arena.Arena;
import colormatch.core.ColorMatch;

public class PlayerHandler {
	
	private ColorMatch plugin;
	private Arena arena;

	public PlayerHandler(ColorMatch plugin, Arena arena) {
		this.plugin = plugin;
		this.arena = arena;
	}

	// check if player can join the arena
	public boolean checkJoin(Player player) {
		if (arena.getStructureManager().getGameLevel().getWorld() == null) {
			player.sendMessage("Arena world is unloaded, can't join arena");
			return false;
		}
		if (!arena.getStatusManager().isArenaEnabled()) {
			player.sendMessage("Arena is not enabled");
			return false;
		}
		if (arena.getStatusManager().isArenaRunning()) {
			player.sendMessage("Arena is already running");
			return false;
		}
		if (player.isInsideVehicle()) {
			player.sendMessage("You can't join the game while sitting inside vehicle");
			return false;
		}
		if (arena.getPlayersManager().getPlayersCount() == arena.getStructureManager().getMaxPlayers()) {
			player.sendMessage("Arena is full");
			return false;
		}
		return true;
	}

	// spawn player on arena
	@SuppressWarnings("deprecation")
	public void spawnPlayer(final Player player, String msgtoplayer, String msgtoarenaplayers) {
		// change player status
		plugin.pdata.storePlayerGameMode(player);
		player.setFlying(false);
		player.setAllowFlight(false);
		plugin.pdata.storePlayerInventory(player);
		plugin.pdata.storePlayerArmor(player);
		plugin.pdata.storePlayerPotionEffects(player);
		plugin.pdata.storePlayerHunger(player);
		// teleport player to arena
		plugin.pdata.storePlayerLocation(player);
		Vector s = arena.getStructureManager().getGameLevel().getSpawnPoint();
		player.teleport(new Location(arena.getStructureManager().getGameLevel().getWorld(), s.getX(), s.getY(), s.getZ()));
		// update inventory
		player.updateInventory();
		// send message to player
		player.sendMessage("You have joined the arena");
		// send message to other players and update bar
		for (String pname : arena.getPlayersManager().getPlayersInArena()) {
			Bukkit.getPlayerExact(pname).sendMessage("Player "+player.getName()+" joined the arena");
		}
		// set player on arena data
		arena.getPlayersManager().addPlayerToArena(player.getName());
		// check for game start
		if (!arena.getStatusManager().isArenaStarting() && arena.getPlayersManager().getPlayersCount() == arena.getStructureManager().getMinPlayers()) {
		}
	}

	// remove player from arena
	@SuppressWarnings("deprecation")
	public void leavePlayer(Player player, String msgtoplayer, String msgtoarenaplayers) {
		// remove player on arena data
		arena.getPlayersManager().removePlayerFromArena(player.getName());
		// restore location ot teleport to lobby
		plugin.pdata.restorePlayerLocation(player);
		// restore player status
		plugin.pdata.restorePlayerHunger(player);
		plugin.pdata.restorePlayerPotionEffects(player);
		plugin.pdata.restorePlayerArmor(player);
		plugin.pdata.restorePlayerInventory(player);
		plugin.pdata.restorePlayerGameMode(player);
		// update inventory
		player.updateInventory();
		// send message to player
		player.sendMessage("You left the arena");
		// send message to other players
		for (String pname : arena.getPlayersManager().getPlayersInArena()) {
			Bukkit.getPlayerExact(pname).sendMessage("Player "+player.getName()+" left the arena");
		}
	}

}
