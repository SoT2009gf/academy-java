package sk.tsystems.gamestudio.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private int ident;
	
	private long time;

	private String userName;

	private String game;

	private String content;

	public Comment() {}

	public Comment(long time, String userName, String game, String content) {
		this.time = time;
		this.userName = userName;
		this.game = game;
		this.content = content;
	}

	public int getIdent() {
		return ident;
	}

	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		
		return sdf.format(new Date(time)).toString();
	}

	public String getUserName() {
		return userName;
	}

	public String getGame() {
		return game;
	}

	public String getContent() {
		return content;
	}
}