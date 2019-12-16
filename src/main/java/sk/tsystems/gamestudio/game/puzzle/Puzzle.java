package sk.tsystems.gamestudio.game.puzzle;

import sk.tsystems.gamestudio.Game;
import sk.tsystems.gamestudio.game.puzzle.UserInterface;
import sk.tsystems.gamestudio.game.puzzle.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.game.puzzle.core.Field;

public class Puzzle implements Game{
	
	private UserInterface userInterface;
	
	private long startMillis;
	
	private static Puzzle instance;
	
	private final int difficultyLevel;
	
	private final Difficulty[] difficulty;
	
	public Puzzle(int difficultyLevel) {
		instance = this;
		this.difficultyLevel = difficultyLevel;
		difficulty = new Difficulty[4];
		difficulty[0] = new Difficulty(3, 3);
		difficulty[1] = new Difficulty(4, 4);
		difficulty[2] = new Difficulty(5, 6);
		// Test difficulty
		difficulty[3] = new Difficulty(2, 2);
		userInterface = new ConsoleUI();
//		Field field = new Field(4, 4);
//		userInterface.newGameStarted(field);
	}

	public static void main(String[] args) {
//		new Puzzle();
	}
	
	public static Puzzle getInstance() {
		return instance;
	}
	
	public long getPlayingSeconds() {
		return (System.currentTimeMillis() - startMillis) / 1000;
	}

	@Override
	public long play() { 
		Field field = new Field(difficulty[difficultyLevel].getRowCount(), difficulty[difficultyLevel].getColumnCount());
		startMillis = System.currentTimeMillis();
		return userInterface.newGameStarted(field);
	}

	@Override
	public String getName() {
		return "puzzle";
	}
}
