package sk.tsystems.gamestudio.game.guessthenumber;

public class Difficulty {

	private final int bottom;
	private final int top;
	
	public Difficulty(int bottom, int top) {
		this.bottom = bottom;
		this.top = top;
	}

	public int getBottom() {
		return bottom;
	}

	public int getTop() {
		return top;
	}
}
