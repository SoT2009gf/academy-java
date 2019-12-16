package sk.tsystems.gamestudio.game.minesweeper;

import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.Game;
import sk.tsystems.gamestudio.game.minesweeper.consoleui.ConsoleUI;

/**
 * Main application class.
 */
public class Minesweeper implements Game {

	/** User interface. */
	private UserInterface userInterface;

	/**
	 * Game start time in milliseconds.
	 */
	private long startMillis;

	/**
	 * List of players with best times.
	 */
	private BestTimes bestTimes;

	/**
	 * Instance of game.
	 */
	private static Minesweeper instance;

	/**
	 * Game settings.
	 */
	private Settings[] settings;
	
	/**
	 * Level of difficulty.
	 */
	private final int difficultyLevel;

	/**
	 * Constructor.
	 */
	public Minesweeper(int difficultyLevel) {
		instance = this;
		this.difficultyLevel = difficultyLevel; 
		settings = new Settings[4];
		settings[0] = Settings.BEGINNER;
		settings[1] = Settings.INTERMEDIATE;
		settings[2] = Settings.EXPERT;
		// Test settings
		settings[3] = new Settings(9, 9, 1);
//		bestTimes = new BestTimes();
		userInterface = new ConsoleUI();
//		settings = Settings.load();
	}

	/**
	 * Main method.
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
//		new Minesweeper();
	}

	/**
	 * Returns game playing time in seconds.
	 * 
	 * @return game playing time in seconds
	 */
	public long getPlayingSeconds() {
		return (System.currentTimeMillis() - startMillis) / 1000;
	}

	/**
	 * returns instance of best times list.
	 * 
	 * @return instance of best times list
	 */
	public BestTimes getBestTimes() {
		return bestTimes;
	}

	/**
	 * Returns instance of game.
	 * 
	 * @return instance of game
	 */
	public static Minesweeper getInstance() {
		return instance;
	}

	/**
	 * Returns game settings.
	 * 
	 * @return game settings
	 */
//	public Settings getSettings() {
//		return settings;
//	}

	/**
	 * Changes game settings and saves them to a file.
	 * 
	 * @param settings game settings
	 */
//	public void setSettings(Settings settings) {
//		this.settings = settings;
//		settings.save();
//	}

	/**
	 * Starts a new game of Minesweeper.
	 */
	public void newGame() {
//        Field field = new Field(settings.getRowCount(), settings.getColumnCount(), settings.getMineCount());
//		Field field = new Field(9,9,1);
		startMillis = System.currentTimeMillis();
//        userInterface.newGameStarted(field);
	}

	@Override
	public long play() {
//		Field field = new Field(settings.getRowCount(), settings.getColumnCount(), settings.getMineCount());
		Field field = new Field(settings[difficultyLevel].getRowCount(), settings[difficultyLevel].getColumnCount(),
				settings[difficultyLevel].getMineCount());
		startMillis = System.currentTimeMillis();
		return userInterface.newGameStarted(field);
	}

	/**
	 * Returns game name.
	 */
	@Override
	public String getName() {
		return "minesweeper";
	}
}
