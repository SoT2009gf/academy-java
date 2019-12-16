package sk.tsystems.gamestudio.game.minesweeper.core;

/**
 * Clue tile.
 */
public class Clue extends Tile {
    /** Value of the clue. */
    private final int value;
    
    /**
     * Returns value of the clue.   
     * @return value of the clue
     */
    public int getValue() {
		return value;
	}

	/**
     * Constructor.
     * @param value  value of the clue
     */
    public Clue(int value) {
        this.value = value;
    }
}
