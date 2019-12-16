package sk.tsystems.gamestudio.game.puzzle.consoleui;

import java.util.Scanner;

import sk.tsystems.gamestudio.game.puzzle.Puzzle;
import sk.tsystems.gamestudio.game.puzzle.UserInterface;
import sk.tsystems.gamestudio.game.puzzle.core.Field;

public class ConsoleUI implements UserInterface {

	private Field field;

	private Scanner scanner;

	public ConsoleUI() {
		scanner = new Scanner(System.in);
	}

	@Override
	public long newGameStarted(Field field) {
		this.field = field;
		do {
			update();
			if (field.isSolved()) {
				long score = field.getRowCount() * field.getColumnCount() * 19 - Puzzle.getInstance().getPlayingSeconds();
				System.out.println("Congratulations, You won achieving score of " + score + " points.");
				return score;
			}
		} while (processInput());

		return 0;
	}

	@Override
	public void update() {
		for (int i = 0; i < field.getRowCount(); i++) {
			for (int j = 0; j < field.getColumnCount(); j++) {
				if (field.getTile(i, j) != null) {
					System.out.printf("%2d ", field.getTile(i, j).getValue());
				} else {
					System.out.print("-- ");
				}
			}
			System.out.println();
		}
	}

	private boolean processInput() {
		System.out.println("Please enter number of a tile next to the empty place or X to exit the program:");
		String input = scanner.nextLine();
		if (input.equalsIgnoreCase("X")) {
//			System.out.println("Program terminated.");
			return false;
		}
		try {
			int tileNumber = Integer.parseInt(input);
			if (tileNumber <= 0 || tileNumber >= field.getRowCount() * field.getColumnCount()) {
				System.err.println("Wrong tile number.");
			}
			field.move(tileNumber);
		} catch (NumberFormatException ex) {
			System.err.println("Wrong input format.");
		}
		return true;
	}
}