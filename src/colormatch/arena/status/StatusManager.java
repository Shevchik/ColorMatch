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
		return false;
	}

	public void disableArena() {
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
