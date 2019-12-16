package sk.tsystems.gamestudio.game.minesweeper.core;

import java.util.Random;

import sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Returns row count.
	 * 
	 * @return row count
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * Returns column count.
	 * 
	 * @return column count
	 */
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * Returns mine count.
	 * 
	 * @return mine count
	 */
	public int getMineCount() {
		return mineCount;
	}

	/**
	 * Returns game state.
	 * 
	 * @return game state
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * Returns tile at specified row and column.
	 * 
	 * @param row    row
	 * @param column column
	 * @return tile
	 */
	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	/**
	 * Constructor.
	 *
	 * @param rowCount    row count
	 * @param columnCount column count
	 * @param mineCount   mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		// generate the field content
		generate();
	}

	/**
	 * Opens tile at specified indeces.
	 *
	 * @param row    row number
	 * @param column column number
	 */
	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}

			if (tile instanceof Clue) {
				if (((Clue) tile).getValue() == 0)
					openAdjacentTiles(row, column);
			}

			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			}
		}
	}

	/**
	 * Opens tiles with type clue adjacent to position [row, column].
	 * 
	 * @param row    row
	 * @param column column
	 */
	private void openAdjacentTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						Tile tile = tiles[actRow][actColumn];
						if (tile instanceof Clue && tile.getState() == State.CLOSED) {
							tile.setState(State.OPEN);
							if (((Clue) tile).getValue() == 0) {
								openAdjacentTiles(actRow, actColumn);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Marks tile at specified indeces.
	 *
	 * @param row    row number
	 * @param column column number
	 */
	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		} else if (tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.CLOSED);
		}
	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		Random randomNumberGenerator = new Random();
		int randomRow;
		int randomColumn;

		int i = 0;
		while (i < mineCount) {
			randomRow = randomNumberGenerator.nextInt(rowCount);
			randomColumn = randomNumberGenerator.nextInt(columnCount);
			if (tiles[randomRow][randomColumn] == null) {
				tiles[randomRow][randomColumn] = new Mine();
				i++;
			}
		}

		for (i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (tiles[i][j] == null) {
					tiles[i][j] = new Clue(countAdjacentMines(i, j));
				}
			}
		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		return rowCount * columnCount - getNumberOf(Tile.State.OPEN) == mineCount;
	}

	/**
	 * Returns number of tiles of given state.
	 * 
	 * @param state
	 * @return number of tiles
	 */
	private int getNumberOf(Tile.State state) {
		int tileCount = 0;

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (tiles[i][j].getState() == state) {
					tileCount++;
				}
			}
		}

		return tileCount;
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row    row number.
	 * @param column column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	/**
	 * Returns count of remaining mines.
	 * @return count of remaining mines
	 */
	public int getRemainingMineCount() {
		int remainingMines = mineCount - getNumberOf(State.MARKED); 
		return remainingMines >= 0 ? remainingMines : 0;
	}
}
