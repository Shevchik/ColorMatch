package colormatch.arena.status;

import colormatch.arena.Arena;

public class StatusManager {

	private Arena arena;
	public StatusManager(Arena arena) {
		this.arena = arena;
	}

	private boolean enabled = false;
	private boolean starting = false;
	private boolean running = false;

	public boolean isArenaEnabled() {
		return enabled;
	}

	public boolean enableArena() {
		if (arena.getStructureManager().isArenaConfigured()) {
			enabled = true;
			return true;
		}
		return false;
	}

	public void disableArena() {
		enabled = false;
		if (arena.getStructureManager().getGameLevel().getSpawnPoint() != null) {
			arena.getStructureManager().getGameLevel().regen();
		}
	}

	public boolean isArenaStarting() {
		return starting;
	}

	public void setStarting(boolean starting) {
		this.starting = starting;
	}

	public boolean isArenaRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
