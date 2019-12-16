package sk.tsystems.gamestudio.game.puzzle;

public class Difficulty {

	private final int rowCount;
	private final int columnCount;
	
	public Difficulty(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	
}
