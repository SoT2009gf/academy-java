package sk.tsystems.gamestudio.game.minesweeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
	/** List of best player times. */
	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	/**
	 * Returns an iterator over a set of best times.
	 * 
	 * @return an iterator
	 */
	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	/**
	 * Adds player time to best times.
	 * 
	 * @param name name of the player
	 * @param time player time in seconds
	 */
	public void addPlayerTime(String name, int time) {
		playerTimes.add(new PlayerTime(name, time));
		Collections.sort(playerTimes);
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {
		Formatter formatter = new Formatter();
		StringBuilder list = new StringBuilder();
		list.append("Best times:\n");
		for (int i = 0; i < playerTimes.size(); i++) {
			list.append(
				formatter.format("%d. %s %d\n", i + 1, playerTimes.get(i).getName(), playerTimes.get(i).getTime()));
		}

		formatter.close();
		return list.toString();
	}

	/**
	 * Player time.
	 */
	public static class PlayerTime implements Comparable<PlayerTime> {
		/** Player name. */
		private final String name;

		/** Playing time in seconds. */
		private final int time;

		/**
		 * Constructor.
		 * 
		 * @param name player name
		 * @param time playing game time in seconds
		 */
		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		/**
		 * Returns player's name.
		 * 
		 * @return player's name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns player's game time.
		 * 
		 * @return player's game time
		 */
		public int getTime() {
			return time;
		}

		/**
		 * Compares current player's time with specified one.
		 */
		@Override
		public int compareTo(PlayerTime playerTime) {
			return this.getTime() - playerTime.getTime();
		}

		/**
		 * Adds a players to the list.
		 * 
		 * @param name player's name
		 * @param time player's game time
		 */
	}

	/**
	 * Clears the list of players and their times.
	 */
	public void reset() {
		playerTimes.clear();
	}
}