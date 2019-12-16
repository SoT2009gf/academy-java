package sk.tsystems.gamestudio.game.minesweeper.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Mine;

public class FieldTest {
	Field field = new Field(ROWS, COLUMNS, MINES);
	
	static final int ROWS = 9;
	
	static final int COLUMNS = 9;
	
	static final int MINES = 10;

	@Test
	public void isSolved() {

		assertEquals(GameState.PLAYING, field.getState());

		int open = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Clue) {
					field.openTile(row, column);
					open++;
				}
				if (field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
					assertEquals(GameState.SOLVED, field.getState());
				} else {
					assertNotSame(GameState.FAILED, field.getState());
				}
			}
		}

		assertEquals(GameState.SOLVED, field.getState());
	}

	@Test
	public void generate() {
		int mineCount = 0;
		int clueCount = 0;
		
		assertEquals(ROWS, field.getRowCount());
		assertEquals(COLUMNS, field.getColumnCount());
		assertEquals(MINES, field.getMineCount());

		for (int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++){
				assertNotNull(field.getTile(i, j));
			}
		}
		
		for (int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++){
				if(field.getTile(i, j) instanceof Mine)
					mineCount++;
			}
		}
		assertEquals(MINES, mineCount);
		
		for (int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++){
				if(field.getTile(i, j) instanceof Clue)
					clueCount++;
			}
		}
		assertEquals(ROWS * COLUMNS - MINES, clueCount);
	}
}