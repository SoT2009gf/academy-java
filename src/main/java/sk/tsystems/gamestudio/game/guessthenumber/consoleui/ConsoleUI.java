package sk.tsystems.gamestudio.game.guessthenumber.consoleui;

import java.util.Scanner;

import sk.tsystems.gamestudio.game.guessthenumber.GuessTheNumber;
import sk.tsystems.gamestudio.game.guessthenumber.UserInterface;
import sk.tsystems.gamestudio.game.guessthenumber.core.GuessTheNumberLogic;

public class ConsoleUI implements UserInterface {

	private Scanner scanner;
	
	public ConsoleUI() {
		scanner = new Scanner(System.in);
	}

	@Override
	public long newGame(int bottom, int top) {
		int guessedNumber;
		String answer;
		
		GuessTheNumberLogic guessTheNumberLogic = new GuessTheNumberLogic(bottom, top);
		guessTheNumberLogic.thinkNumber();
		System.out.println("I think of a number...");
		System.out.println("Enter, which one or press X to quit...");
		do {			
			answer = scanner.nextLine().toUpperCase();
			try {
				if(answer.equals("X")) {
					return 0;
				}
				guessedNumber = Integer.parseInt(answer);
				if(guessTheNumberLogic.guess(guessedNumber) == 0) {
					long score = (top - bottom) * 15 - GuessTheNumber.getInstance().getPlayingSeconds();
					System.out.println("Correct! Your score is " + score + " points.");					
					return score;
				} else if(guessTheNumberLogic.guess(guessedNumber) > 0) {
					System.out.println("Greater.");
				} else if(guessTheNumberLogic.guess(guessedNumber) < 0){
					System.out.println("Lower.");
				}
				
			} catch(NumberFormatException ex) {
				System.err.println("Wrong input format.");
				continue;
			}
		} 
		while(true);		
	}
}
