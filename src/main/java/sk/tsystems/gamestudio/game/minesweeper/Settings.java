package sk.tsystems.gamestudio.game.minesweeper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Provides settings.
 */
public class Settings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Row count.
	 */
	private final int rowCount;

	/**
	 * Column count.
	 */
	private final int columntCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Settings for beginner players.
	 */
	public static final Settings BEGINNER = new Settings(9, 9, 10);

	/**
	 * Settings for intermediate players.
	 */
	public static final Settings INTERMEDIATE = new Settings(16, 16, 40);

	/**
	 * Settings for expert players.
	 */
	public static final Settings EXPERT = new Settings(16, 30, 99);

	/**
	 * Saved settings file location.
	 */
	private static final String SETTINGS_FILE = System.getProperty("user.home") + System.getProperty("file.separator")
			+ "minesweeper.settings";

	/**
	 * Constructor.
	 * 
	 * @param rowCount     row count
	 * @param columntCount column count
	 * @param mineCount    mine count
	 */
	public Settings(int rowCount, int columntCount, int mineCount) {
		this.rowCount = rowCount;
		this.columntCount = columntCount;
		this.mineCount = mineCount;
	}

	/**
	 * Returns row count.
	 * 
	 * @return row count
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * Returns column count.
	 * 
	 * @return column count
	 */
	public int getColumnCount() {
		return columntCount;
	}

	/**
	 * Returns mine count.
	 * 
	 * @return mine count
	 */
	public int getMineCount() {
		return mineCount;
	}

	@Override
	public int hashCode() {
		return rowCount * columntCount * mineCount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Settings other = (Settings) obj;
		if (columntCount != other.columntCount)
			return false;
		if (mineCount != other.mineCount)
			return false;
		if (rowCount != other.rowCount)
			return false;
		return true;
	}

	/**
	 * Saves settings to a file.
	 */
	public void save() {
		try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(SETTINGS_FILE, false))) {
			objOut.writeObject(this);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Loads settings from a file.
	 * 
	 * @return settings
	 */
	public static Settings load() {
		try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(SETTINGS_FILE))) {
			return (Settings) objIn.readObject();
		} catch (IOException ex) {
			return BEGINNER;
		} catch (ClassNotFoundException ex) {
			return BEGINNER;
		} catch (ClassCastException ex) {
			return BEGINNER;
		}
	}
}