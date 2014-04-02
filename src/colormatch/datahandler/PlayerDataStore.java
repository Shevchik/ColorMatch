package colormatch.datahandler;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class PlayerDataStore {

	private HashMap<String, ItemStack[]> plinv = new HashMap<String, ItemStack[]>();
	private HashMap<String, ItemStack[]> plarmor = new HashMap<String, ItemStack[]>();
	private HashMap<String, Collection<PotionEffect>> pleffects = new HashMap<String, Collection<PotionEffect>>();
	private HashMap<String, Location> plloc = new HashMap<String, Location>();
	private HashMap<String, Integer> plhunger = new HashMap<String, Integer>();
	private HashMap<String, GameMode> plgamemode = new HashMap<String, GameMode>();

	public void storePlayerInventory(Player player) {
		PlayerInventory pinv = player.getInventory();
		plinv.put(player.getName(), pinv.getContents());
		pinv.clear();
	}

	public void storePlayerArmor(Player player) {
		PlayerInventory pinv = player.getInventory();
		plarmor.put(player.getName(), pinv.getArmorContents());
		pinv.setArmorContents(null);
	}

	public void storePlayerPotionEffects(Player player) {
		Collection<PotionEffect> peff = player.getActivePotionEffects();
		pleffects.put(player.getName(), peff);
		for (PotionEffect peffect : peff) {
			player.removePotionEffect(peffect.getType());
		}
	}

	public void storePlayerLocation(Player player) {
		plloc.put(player.getName(), player.getLocation());
	}

	public void storePlayerHunger(Player player) {
		plhunger.put(player.getName(), player.getFoodLevel());
		player.setFoodLevel(20);
	}

	public void storePlayerGameMode(Player player) {
		plgamemode.put(player.getName(), player.getGameMode());
		player.setGameMode(GameMode.SURVIVAL);
	}

	public void restorePlayerInventory(Player player) {
		player.getInventory().setContents(plinv.get(player.getName()));
		plinv.remove(player.getName());
	}

	public void restorePlayerArmor(Player player) {
		player.getInventory().setArmorContents(plarmor.get(player.getName()));
		plarmor.remove(player.getName());
	}

	public void restorePlayerPotionEffects(Player player) {
		player.addPotionEffects(pleffects.get(player.getName()));
		pleffects.remove(player.getName());
	}

	public void restorePlayerLocation(Player player) {
		player.teleport(plloc.get(player.getName()));
		plloc.remove(player.getName());
	}

	public void restorePlayerHunger(Player player) {
		player.setFoodLevel(plhunger.get(player.getName()));
		plhunger.remove(player.getName());
	}

	public void restorePlayerGameMode(Player player) {
		player.setGameMode(plgamemode.get(player.getName()));
		plgamemode.remove(player.getName());
	}

}
