package sk.tsystems.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GameRating {
	
	@Id
	@GeneratedValue
	private int ident;

	private String userName;

	private String game;
	
	private int rating;

	public GameRating() {}

	public GameRating(String userName, String game, int rating) {
		this.userName = userName;
		this.game = game;
		this.rating = rating;
	}

	public int getIdent() {
		return ident;
	}

	public String getUserName() {
		return userName;
	}

	public String getGame() {
		return game;
	}

	public int getRating() {
		return rating;
	}
}