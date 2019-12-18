package sk.tsystems.gamestudio.controller;

import java.util.Formatter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.puzzle.core.Field;
import sk.tsystems.gamestudio.game.puzzle.core.Tile;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/puzzle")
public class PuzzleController {

	private Field field;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private MainController mainController;

	@RequestMapping
	public String index() {
		field = new Field(4, 4);
		return "puzzle";
	}

	@RequestMapping("/move")
	public String move(int tile) {
		if (!field.isSolved()) {
			field.move(tile);
			if (field.isSolved() && mainController.isLogged()) {
				Score score = new Score(mainController.getLoggedPlayer().getName(), "puzzle", field.getScore());
				scoreService.addScore(score);
			}
		}
		return "puzzle";
	}

	public String getHtmlField() {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		formatter.format("<table>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			formatter.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				formatter.format("<td>\n");
				Tile tile = field.getTile(row, column);
				if (tile != null) {
					formatter.format(
							"<a href='/puzzle/move?tile=%d'><img src='/img/puzzle/img%d.jpg' alt='Puzzle piece number %d.'/></a>",
							tile.getValue(), tile.getValue(), tile.getValue());
				}
				formatter.format("</td>\n");
			}
			formatter.format("</tr>\n");
		}
		formatter.format("</table>\n");
		return formatter.toString();
	}

	public boolean isSolved() {
		return field.isSolved();
	}

	public List<Score> getScores() {
		return scoreService.getTopScores("puzzle");
	}
}