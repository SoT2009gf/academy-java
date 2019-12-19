package sk.tsystems.gamestudio.game.pairs.core;

public class Tile {

	private final int value;
	
	private State state;
	
	public Tile(int value) {
		this.value = value;
		this.setState(State.CLOSED);
	}
	
	public int getValue() {
		return value;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (state != other.state)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
}
