package sk.tsystems.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private int ident;

	private String userName;

	private String game;

	private String comment;

	public Comment() {}

	public Comment(String userName, String game, String comment) {
		this.userName = userName;
		this.game = game;
		this.comment = comment;
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

	public String getComment() {
		return comment;
	}
}