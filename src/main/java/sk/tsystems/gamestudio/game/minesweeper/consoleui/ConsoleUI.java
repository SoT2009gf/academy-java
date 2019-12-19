package sk.tsystems.gamestudio.game.minesweeper.consoleui;

import java.io.BufferedReader;



import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Mine;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;
import sk.tsystems.gamestudio.game.minesweeper.Minesweeper;
import sk.tsystems.gamestudio.game.minesweeper.UserInterface;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input;

	/** Constructor. */
	public ConsoleUI() {
		input = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Starts the game.
	 * 
	 * @param field field of mines and clues
	 */
	@Override
	public long newGameStarted(Field field) {
		this.field = field;
		do {
			update();
			if (field.getState() == GameState.SOLVED) {
				long score = field.getRowCount() * field.getColumnCount() * field.getMineCount() * 3 - Minesweeper.getInstance().getPlayingSeconds(); 
				System.out.println("Congratulations, You have found all mines and achieved score of " + score + " points.");	
				return score;
			}
			if (field.getState() == GameState.FAILED) {
				System.out.println("You have stepped on the mine and died...");
				return 0;
			}
		} while (processInput());
		return 0;
	}

	/**
	 * Updates user interface - prints the field.
	 */
	@Override
	public void update() {
		System.out.print("  ");
		for (int i = 0; i < field.getColumnCount(); i++) {
			System.out.print(i + " ");
		}

		System.out.println();

		for (int i = 0; i < field.getRowCount(); i++) {
			System.out.printf("%c ", 'A' + i);
			for (int j = 0; j < field.getColumnCount(); j++) {
				Tile tile = field.getTile(i, j);
				if (tile.getState() == Tile.State.CLOSED) {
					System.out.printf("%c ", '-');
				} else if (tile.getState() == Tile.State.MARKED) {
					System.out.printf("%c ", 'M');
				} else if (tile.getState() == Tile.State.OPEN) {
					if (tile instanceof Mine) {
						System.out.printf("%c ", 'X');
					} else if (tile instanceof Clue) {
						System.out.printf("%d ", ((Clue) tile).getValue());
					}
				}
			}
			System.out.println();			
		}
		System.out.println("Remaining mines: " + field.getRemainingMineCount());
		System.out.println("Game time: " + Minesweeper.getInstance().getPlayingSeconds() + " seconds");
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to the input string.
	 */
	private boolean processInput() {
		System.out.println("Please enter your selection (X) EXIT, (MA1) MARK, (OB4) OPENED:");
		String answer = readLine();

		try {
			return handleInput(answer);
		} catch (WrongFormatException ex) {
			System.err.println(ex.getMessage());
		}
		return true;
	}

	/**
	 * Handles user input.
	 * @param input input
	 * @throws WrongFormatException in case of wrong input format 
	 */
	private boolean handleInput(String input) throws WrongFormatException {		
		input = input.trim();
		input = input.toUpperCase();
		Pattern pattern = Pattern.compile("((M|O)([A-Z])([0-9]))");
		Matcher matcher = pattern.matcher(input);

		if(input == null) {
			System.out.println("Error during input processing.");
		}
		
		if (input.equals("X")) {
//			System.out.println("Program terminated.");
			return false;
		}

		if (!matcher.matches()) {
			throw new WrongFormatException("Wrong input format.");
		}

		int enteredRow = matcher.group(3).charAt(0) - 'A';
		int enteredColumn = matcher.group(4).charAt(0) - '0';

		if (matcher.group(2).equals("M")) {
			field.markTile(enteredRow, enteredColumn);
		}

		if (matcher.group(2).equals("O")) {
			field.openTile(enteredRow, enteredColumn);
		}
		return true;
	}	
}
