package sk.tsystems.gamestudio.game.minesweeper;

import sk.tsystems.gamestudio.game.minesweeper.core.Field;

/**
 * Current user interface.
 */
public interface UserInterface {

	/**
	 * Starts the game.
	 * @param field field of mines and clues
	 */
	long newGameStarted(Field field);

	/**
	 * Updates user interface - prints the field.
	 */
	void update();
}