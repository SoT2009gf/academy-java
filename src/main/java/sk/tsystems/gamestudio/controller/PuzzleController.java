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
import sk.tsystems.gamestudio.game.puzzle.core.Field;
import sk.tsystems.gamestudio.game.puzzle.core.Tile;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/puzzle")
public class PuzzleController {

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
		field = new Field(4, 4);
		startMillis = System.currentTimeMillis();
		return "puzzle";
	}

	@RequestMapping("/move")
	public String move(int tile) {
		if (!field.isSolved()) {
			field.move(tile);
			if (field.isSolved() && mainController.isLogged()) {
				scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "puzzle",
						(int)(field.getRowCount() * field.getColumnCount() * 19 - getPlayingSeconds())));
			}
		}
		return "puzzle";
	}

	public String getHtmlField() {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		for (int row = 0; row < field.getRowCount(); row++) {
			formatter.format("<div class='puzzle-row'>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				formatter.format("<div class='puzzle-column'>\n");
				Tile tile = field.getTile(row, column);
				if (tile != null) {
					formatter.format(
							"<a href='/puzzle/move?tile=%d'><img src='/img/puzzle/img%d.jpg' alt='Puzzle piece number %d.'/></a>",
							tile.getValue(), tile.getValue(), tile.getValue());
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
		return scoreService.getTopScores("puzzle");
	}

	public List<Comment> getComments() {
		return commentService.getComments("puzzle");
	}

	public double getRating() {
		return ratingService.getRatingAvg("puzzle");
	}
}