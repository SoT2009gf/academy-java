package sk.tsystems.gamestudio.game.puzzle.core;

import java.util.Random;

public class Field {
	private final int rowCount;

	private final int columnCount;

	private int emptyTileRow;

	private int emptyTileColumn;

	private final Tile[][] tiles;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new Tile[rowCount][columnCount];
		emptyTileRow = rowCount - 1;
		emptyTileColumn = columnCount - 1;

		generate();
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	private void generate() {
		int tileNumber = 1;

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				tiles[i][j] = new Tile(tileNumber);
				tileNumber++;
			}
		}

		tiles[emptyTileRow][emptyTileColumn] = null;

		shuffleField();
	}

	public void move(int tileNumber) {
		if (exchangeEmptyTile(-1, 0, tileNumber)) {
			;
		} else if (exchangeEmptyTile(1, 0, tileNumber)) {
			;
		} else if (exchangeEmptyTile(0, -1, tileNumber)) {
			;
		} else if (exchangeEmptyTile(0, 1, tileNumber)) {
			;
		}
	}

	private boolean exchangeEmptyTile(int deltaRow, int deltaColumn, int tileNumber) {
		if (emptyTileRow + deltaRow >= 0 && emptyTileRow + deltaRow < rowCount && emptyTileColumn + deltaColumn >= 0
				&& emptyTileColumn + deltaColumn < columnCount
				&& tiles[emptyTileRow + deltaRow][emptyTileColumn + deltaColumn].getValue() == tileNumber) {
			tiles[emptyTileRow][emptyTileColumn] = tiles[emptyTileRow + deltaRow][emptyTileColumn + deltaColumn];
			emptyTileRow = emptyTileRow + deltaRow;
			emptyTileColumn = emptyTileColumn + deltaColumn;
			tiles[emptyTileRow][emptyTileColumn] = null;
			return true;
		}
		return false;
	}

	public boolean isSolved() {
		int tileNumber = 1;
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (tiles[i][j] != null && tiles[i][j].getValue() != tileNumber) {
					return false;
				}
				tileNumber++;
			}
		}
		return true;
	}

	private void shuffleField() {
		Random rng = new Random();
		for (int i = 0; i < rowCount * columnCount * 1000; i++) {
			move(rng.nextInt(rowCount * columnCount - 1) + 1);
		}
	}
}