package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/minesweeper")
public class MinesweeperController {

	private Field field;

	private long startMillis;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private MainController mainController;

	@RequestMapping
	public String index() {
		field = new Field(9, 9, 10);
		startMillis = System.currentTimeMillis();
		return "minesweeper";
	}

	@RequestMapping("/open")
	public String open(int row, int column) {
		if (field.getState() == GameState.PLAYING) {
			field.openTile(row, column);
			if (field.getState() == GameState.SOLVED && mainController.isLogged()) {
				int scoreValue = (int) (field.getRowCount() * field.getColumnCount() * field.getMineCount() * 3
						- getPlayingSeconds());
				scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "minesweeper",
						scoreValue > 0 ? scoreValue : 0));
			}
		}
		return "minesweeper";
	}

	@RequestMapping("/mark")
	public String mark(int row, int column) {
		if (field.getState() == GameState.PLAYING) {
			field.markTile(row, column);
		}
		return "minesweeper";
	}

	public String getHtmlField() {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();

		for (int row = 0; row < field.getRowCount(); row++) {
			formatter.format("<div class='minesweeper-row'>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				formatter.format("<div class='minesweeper-column'>\n");
				Tile tile = field.getTile(row, column);
				formatter.format("<a href='/minesweeper/open?row=%d&column=%d'class='tile'>", row, column);
				formatter.format("<img src='/img/minesweeper/%s.png'></a>", getImageName(tile));
				formatter.format("</a>");
				formatter.format("</div>\n");
			}
			formatter.format("</div>\n");
		}

		return formatter.toString();
	}

	private String getImageName(Tile tile) {
		switch (tile.getState()) {
		case CLOSED:
			return "closed";
		case MARKED:
			return "marked";
		case OPEN:
			if (tile instanceof Clue)
				return "open" + ((Clue) tile).getValue();
			else
				return "mine";
		default:
			throw new IllegalArgumentException();
		}
	}

	public boolean isSolved() {
		return field.isSolved();
	}

	public boolean isFailed() {
		return field.getState() == GameState.FAILED;
	}

	private long getPlayingSeconds() {
		return (System.currentTimeMillis() - startMillis) / 1000;
	}

	public List<Score> getScores() {
		return scoreService.getTopScores("minesweeper");
	}

	public List<Comment> getComments() {
		return commentService.getComments("minesweeper");
	}

	public double getRating() {
		return ratingService.getRatingAvg("minesweeper");
	}
	
	public String getMineCount() {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		return formatter.format("%03d", field.getRemainingMineCount()).toString();
	}
	
	public String getSeconds() {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		return formatter.format("%03d", getPlayingSeconds()).toString();
	}
}
