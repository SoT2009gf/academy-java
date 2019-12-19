package sk.tsystems.gamestudio.game.pairs.core;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import sk.tsystems.gamestudio.game.pairs.core.Tile;

public class Field {
	private final int rowCount;

	private final int columnCount;

	private Tile openedTile; 
	
	private final Tile[][] tiles;

	public Field() {
		this.rowCount = 4;
		this.columnCount = 4;
		tiles = new Tile[getRowCount()][getColumnCount()];
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
		Random random = new Random();
		int randomRow;
		int randomColumn;

		for (int i = 0; i < 2; i++) {
			int tileNumber = 1;
			while (tileNumber <= 8) {
				randomRow = random.nextInt(rowCount);
				randomColumn = random.nextInt(columnCount);
				if (tiles[randomRow][randomColumn] == null) {
					tiles[randomRow][randomColumn] = new Tile(tileNumber);
					tileNumber++;
				}
			}
		}
	}
	
	public void open(int row, int column) {
		Tile tile = getTile(row, column);
		if(openedTile == null) {
			openedTile = tile;
			tile.setState(State.OPENED);
		} else if(openedTile != null) {
			if(openedTile.getValue() == tile.getValue() && !tile.equals(openedTile)) {
				tile.setState(State.PAIRED);
				openedTile.setState(State.PAIRED);
				openedTile = null;
			} else if(openedTile.getValue() != tile.getValue() && !tile.equals(openedTile)) {
				tile.setState(State.CLOSED);				
				openedTile.setState(State.CLOSED);
				openedTile = null;
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public boolean isSolved() {
		for(int row = 0; row < rowCount; row++) {
			for(int column = 0; column < columnCount; column++) {
				if(getTile(row, column).getState() != State.PAIRED) {
					return false;
				}
			}
		}
		return true;
	}

	public Tile getOpenedTile() {
		return openedTile;
	}
}