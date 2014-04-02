package colormatch.arena.status;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayersManager {

	private HashSet<String> players = new HashSet<String>();

	public boolean isPlayerInArena(String name) {
		return players.contains(name);
	}

	public int getPlayersCount() {
		return players.size();
	}

	public Set<String> getPlayersNamesInArena() {
		return Collections.unmodifiableSet(players);
	}
	
	public Set<Player> getPlayersInArena() {
		HashSet<Player> playersset = new HashSet<Player>();
		for (String playername : players) {
			playersset.add(Bukkit.getPlayerExact(playername));
		}
		return playersset;
	}

	public void addPlayerToArena(String name) {
		players.add(name);
	}

	public void removePlayerFromArena(String name) {
		players.remove(name);
	}

}
