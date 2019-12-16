package sk.tsystems.gamestudio.consoleui;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import sk.tsystems.gamestudio.Game;
import sk.tsystems.gamestudio.Menu;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.guessthenumber.GuessTheNumber;
import sk.tsystems.gamestudio.game.minesweeper.Minesweeper;
import sk.tsystems.gamestudio.game.puzzle.Puzzle;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.ScoreServiceJDBC;

public class ConsoleMenu implements Menu {

	private Scanner scanner;

	public ConsoleMenu() {
		scanner = new Scanner(System.in);
	}

	@Override
	public void display() {
		do {
			update();
		} while (processInput());

		System.out.println("Have a nice day.");
	}

	private void update() {
		System.out.println("Welcome to game studio.");
		System.out.println("Please select a game:");
		System.out.println("----------------------");
		System.out.println("1. Minesweeper");
		System.out.println("2. Puzzle");
		System.out.println("3. Guess the number");
		System.out.println("4. Exit program");
	}

	private boolean processInput() {
		try {
			int input = Integer.parseInt(scanner.nextLine());
			switch (input) {
			case 1:
				runGame(new Minesweeper(3));
				return true;
			case 2:
				runGame(new Puzzle(3));
				return true;
			case 3:
				runGame(new GuessTheNumber(3));
				return true;
			case 4:
				return false;
			default:
				System.err.println("Wrong choice number.");
				return true;
			}
		} catch (NumberFormatException ex) {
			System.err.println("Wrong input format.");
			return true;
		}
	}

	private void runGame(Game game) {
		displayScores(game.getName());
		updateScores(game);
	}

	private void displayScores(String game) {
		ScoreService scoreService = new ScoreServiceJDBC();
		List<Score> topScores = new ArrayList<>();
		topScores = scoreService.getTopScores(game);
		if (topScores.size() > 0) {
			System.out.println("Hall of fame:");
			int position = 1;
			for (Score score : topScores) {
				System.out.println(position + ". " + score.getUserName() + " " + score.getValue());
				position++;
			}
			System.out.println();
		}
	}

	private void updateScores(Game game) {
		long score = game.play();
		if (score > 0) {
			String player = System.getProperty("user.name");
			ScoreService scoreService = new ScoreServiceJDBC();
			scoreService.addScore(new Score(player, game.getName(), (int) score));
		}
	}
}
