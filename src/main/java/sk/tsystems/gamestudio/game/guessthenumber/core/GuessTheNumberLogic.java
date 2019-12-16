package sk.tsystems.gamestudio.game.guessthenumber.core;

import java.util.Random;

public class GuessTheNumberLogic {

	private int bottom;
	
	private int top;
	
	private int thoughtNumber;

	public GuessTheNumberLogic(int bottom, int top) {
		this.bottom = bottom;
		this.top = top;
	}

	public void thinkNumber() {
		Random random = new Random();
		int randomNumber;
		
		do { 
			randomNumber = random.nextInt(top + 1);
		} while(randomNumber < bottom);
		
		thoughtNumber = randomNumber;
	}
	
	public int guess(int number) {
		return thoughtNumber - number;
	}
}
