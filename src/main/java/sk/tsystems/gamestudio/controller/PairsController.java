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
import sk.tsystems.gamestudio.game.pairs.core.Field;
import sk.tsystems.gamestudio.game.pairs.core.State;
import sk.tsystems.gamestudio.game.pairs.core.Tile;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/pairs")
public class PairsController {

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
		field = new Field();
		startMillis = System.currentTimeMillis();
		return "pairs";
	}

	@RequestMapping("/open")
	public String move(int row, int column) {
		if (!field.isSolved()) {
			field.openTile(row, column);
			if (field.isSolved() && mainController.isLogged()) {
				int scoreValue = (int) (field.getRowCount() * field.getColumnCount() * 12 - getPlayingSeconds());
				scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "pairs",
						scoreValue > 0 ? scoreValue : 0));
			}
		}
		return "pairs";
	}

	@RequestMapping("/close")
	public String close() {
		field.closePair();
		return "pairs";
	}

	public String getHtmlField() {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		for (int row = 0; row < field.getRowCount(); row++) {
			formatter.format("<div class='pairs-row'>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				formatter.format("<div class='pairs-column'>\n");
				Tile tile = field.getTile(row, column);
				if (tile.getState() == State.FIRST) {
					formatter.format(
							"<a href='/pairs/open?row=%d&column=%d' class='tile'><img src='/img/pairs/img%d.jpg' alt='Pairs card number %d.'/></a>",
							row, column, tile.getValue(), tile.getValue());
				} else if (tile.getState() == State.SECOND) {
					formatter.format(
							"<a href='/pairs/open?row=%d&column=%d' class='tile secondTile'><img src='/img/pairs/img%d.jpg' alt='Pairs card number %d.'/></a>",
							row, column, tile.getValue(), tile.getValue());
				} else if (tile.getState() == State.CLOSED) {
					formatter.format("<a href='/pairs/open?row=%d&column=%d' class='tile'><img src='/img/pairs/closed.jpg' alt='Pairs card.'/></a>", row, column);
				} else if (tile.getState() == State.PAIRED) {
					formatter.format("<img src='/img/pairs/img%d.jpg' alt='Pairs card number %d.'/>", tile.getValue(), tile.getValue());
				}
				formatter.format("</div>\n");
			}
			formatter.format("</div>\n");
		}
		return formatter.toString();
	}

	public boolean isSolved() {
		return field.isSolved();
	}

	private long getPlayingSeconds() {
		return (System.currentTimeMillis() - startMillis) / 1000;
	}

	public List<Score> getScores() {
		return scoreService.getTopScores("pairs");
	}

	public List<Comment> getComments() {
		return commentService.getComments("pairs");
	}

	public double getRating() {
		return ratingService.getRatingAvg("pairs");
	}
}