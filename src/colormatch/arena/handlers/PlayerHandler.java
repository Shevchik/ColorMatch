package colormatch.arena.handlers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import colormatch.arena.Arena;

public class PlayerHandler {

	private Arena arena;

	public PlayerHandler(Arena arena) {
		this.arena = arena;
	}

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

	@SuppressWarnings("deprecation")
	public void spawnPlayer(final Player player, String msgtoplayer, String msgtoarenaplayers) {
		arena.plugin.pdata.storePlayerGameMode(player);
		player.setFlying(false);
		player.setAllowFlight(false);
		arena.plugin.pdata.storePlayerInventory(player);
		arena.plugin.pdata.storePlayerArmor(player);
		arena.plugin.pdata.storePlayerPotionEffects(player);
		arena.plugin.pdata.storePlayerHunger(player);
		arena.plugin.pdata.storePlayerLocation(player);
		Vector s = arena.getStructureManager().getGameLevel().getSpawnPoint();
		player.teleport(new Location(arena.getStructureManager().getGameLevel().getWorld(), s.getX(), s.getY(), s.getZ()));
		player.updateInventory();
		if (!msgtoplayer.isEmpty()) {
			player.sendMessage(msgtoplayer);
		}
		if (!msgtoarenaplayers.isEmpty()) {
			for (Player oplayer : arena.getPlayersManager().getPlayersInArena()) {
				oplayer.sendMessage(msgtoarenaplayers);
			}
		}
		arena.getPlayersManager().addPlayerToArena(player.getName());
		if (!arena.getStatusManager().isArenaStarting() && arena.getPlayersManager().getPlayersCount() == arena.getStructureManager().getMinPlayers()) {
			arena.getGameHandler().startArena();
		}
	}

	@SuppressWarnings("deprecation")
	public void leavePlayer(Player player, String msgtoplayer, String msgtoarenaplayers) {
		arena.getPlayersManager().removePlayerFromArena(player.getName());
		arena.plugin.pdata.restorePlayerLocation(player);
		arena.plugin.pdata.restorePlayerHunger(player);
		arena.plugin.pdata.restorePlayerPotionEffects(player);
		arena.plugin.pdata.restorePlayerArmor(player);
		arena.plugin.pdata.restorePlayerInventory(player);
		arena.plugin.pdata.restorePlayerGameMode(player);
		player.updateInventory();
		if (!msgtoplayer.isEmpty()) {
			player.sendMessage(msgtoplayer);
		}
		if (!msgtoarenaplayers.isEmpty()) {
			for (Player oplayer : arena.getPlayersManager().getPlayersInArena()) {
				oplayer.sendMessage("Player "+player.getName()+" left the arena");
			}
		}
	}

}
