package sk.tsystems.gamestudio.game.minesweeper.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.tsystems.gamestudio.game.minesweeper.Settings;

public class SettingsTest {
	public static final Settings BEGINNER = new Settings(9, 9, 10);

	public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
	
	public static final Settings EXPERT = new Settings(16, 30, 99);
	
	private static final String SETTINGS_FILE = System.getProperty("user.home") + System.getProperty("file.separator")
	+ "minesweeper.settings";
	
	@Test
	public void constructorTest() {
		assertEquals(9, BEGINNER.getRowCount());
		assertEquals(9, BEGINNER.getColumnCount());
		assertEquals(10, BEGINNER.getMineCount());
		
		assertEquals(16, INTERMEDIATE.getRowCount());
		assertEquals(16, INTERMEDIATE.getColumnCount());
		assertEquals(40, INTERMEDIATE.getMineCount());

		assertEquals(16, EXPERT.getRowCount());
		assertEquals(30, EXPERT.getColumnCount());
		assertEquals(99, EXPERT.getMineCount());
	}
	
	@Before
	public void initLoadSave() {
		File settingsFile = new File(SETTINGS_FILE);
		if(settingsFile.exists() && settingsFile.isFile()) {
			settingsFile.delete();
		}
	}
	
	@Test
	public void loadTestNoSavedData() {
		Settings loadedSettings = Settings.load();
		
		assertTrue((loadedSettings.equals(BEGINNER)));
	}
	
	@Test
	public void saveTest() {
		Settings loadedSettings;
		
		INTERMEDIATE.save();		
		loadedSettings = Settings.load();
		assertTrue((loadedSettings.equals(INTERMEDIATE)));
	}
	
	@Test
	public void saveTestOverwrite() {
		Settings loadedSettings;
		
		EXPERT.save();		
		loadedSettings = Settings.load();
		assertTrue((loadedSettings.equals(EXPERT)));
	}
	
	@After
	public void cleanUp() {
		File settingsFile = new File(SETTINGS_FILE);
		if(settingsFile.exists() && settingsFile.isFile()) {
			settingsFile.delete();
		}
	}
}