package sk.tsystems.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Score {
	
	@Id
	@GeneratedValue
	private int ident;

	private String userName;

	private String game;

	private int value;

	public Score() {}

	public Score(String userName, String game, int value) {
		this.userName = userName;
		this.game = game;
		this.value = value;
	}

	public int getIdent() {
		return ident;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Score [ident=" + ident + ", username=" + userName + ", game=" + game + ", value=" + value + "]";
	}
}
