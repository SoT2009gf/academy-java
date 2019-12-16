package sk.tsystems.gamestudio.game.guessthenumber;

import sk.tsystems.gamestudio.Game;

import sk.tsystems.gamestudio.game.guessthenumber.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.game.guessthenumber.Difficulty;

public class GuessTheNumber implements Game{

	private UserInterface userInterface;
	
	private long startMillis;
	
	private static GuessTheNumber instance;
	
	private final Difficulty[] difficulty;
	
	private final int difficultyLevel;
	
	public GuessTheNumber(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
		difficulty = new Difficulty[4];
		difficulty[0] = new Difficulty(0, 10);
		difficulty[1] = new Difficulty(0, 30);
		difficulty[2] = new Difficulty(0, 50);
		// Test difficulty
		difficulty[3] = new Difficulty(0, 5);
		userInterface = new ConsoleUI();
	}

	public static void main(String[] args) {
//		new GuessTheNumber();
	}

	public static GuessTheNumber getInstance() {
		return instance;
	}

	@Override
	public long play() {
		startMillis = System.currentTimeMillis();
		return userInterface.newGame(difficulty[difficultyLevel].getBottom(), difficulty[difficultyLevel].getTop());
	}

	@Override
	public String getName() {
		return "guessthenumber";
	}

	public long getPlayingSeconds() {
		return (System.currentTimeMillis() - startMillis) / 1000;
	}
}
