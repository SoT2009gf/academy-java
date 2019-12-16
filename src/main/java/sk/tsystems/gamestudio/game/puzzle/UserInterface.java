package sk.tsystems.gamestudio.game.puzzle;

import sk.tsystems.gamestudio.game.puzzle.core.Field;

public interface UserInterface {

	long newGameStarted(Field field);

	void update();
}