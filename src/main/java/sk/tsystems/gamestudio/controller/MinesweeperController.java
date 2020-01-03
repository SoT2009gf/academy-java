package sk.tsystems.gamestudio.controller;

import java.util.Formatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/minesweeper")
public class MinesweeperController {

	private Field field;

	private long startMillis;
	
	private long seconds;
	
	boolean victoryMark;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private MainController mainController;

	@RequestMapping
	public String index() {
		field = new Field(9, 9, 10);
		startMillis = System.currentTimeMillis();
		seconds = 0;
		victoryMark = false;
		return "minesweeper";
	}

	@RequestMapping("/refresh")
	public String refresh() {
		return "minesweeper";
	}
	
	@RequestMapping("/open")
	public String open(int row, int column) {
		if (field.getState() == GameState.PLAYING) {
			field.openTile(row, column);
			seconds = getPlayingSeconds();
			if (field.getState() == GameState.SOLVED && mainController.isLogged()) {
				int scoreValue = (int) (field.getRowCount() * field.getColumnCount() * field.getMineCount() * 3
						- getPlayingSeconds());
				scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "minesweeper",
						scoreValue > 0 ? scoreValue : 0));
			}
		}
		return "minesweeper";
	}

	@RequestMapping("/open-ajax")
	public String openAjax(int row, int column) {
		open(row, column);
		return "minesweeper-field";
	}

	@RequestMapping("/mark")
	public String mark(int row, int column) {
		if (field.getState() == GameState.PLAYING) {
			field.markTile(row, column);
			seconds = getPlayingSeconds();
		}
		return "minesweeper";
	}

	@RequestMapping("/mark-ajax")
	public String markAjax(int row, int column) {
		mark(row, column);
		return "minesweeper-field";
	}

	public String getHtmlField() {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();

		for (int row = 0; row < field.getRowCount(); row++) {
			formatter.format("<div class='minesweeper-row'>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				formatter.format("<div class='minesweeper-column'>\n");
				Tile tile = field.getTile(row, column);
				formatter.format("<a href='/minesweeper/open-ajax?row=%d&column=%d' class='tile'>", row, column);
				formatter.format(getImageName(tile));
				formatter.format("</a>");
				formatter.format("</div>\n");
			}
			formatter.format("</div>\n");
			if(field.getState() == GameState.SOLVED && !victoryMark) {
				victoryMark = true;
				formatter.format("<div id='game-won'></div>\n");
			} 
		}
		return formatter.toString();
	}

	private String getImageName(Tile tile) {
		switch (tile.getState()) {
		case CLOSED:
			return "<img src='/img/minesweeper/closed.png' alt='Closed tile.'/>";
		case MARKED:
			return "<img src='/img/minesweeper/marked.png' alt='Marked tile.'/>";
		case OPEN:
			if (tile instanceof Clue)
				return "<img src='/img/minesweeper/open" + ((Clue) tile).getValue() + ".png' alt='Open tile.'/>";
			else
				return "<img class='mine' src='/img/minesweeper/mine.png' alt='Exploded mine.'/>";
		case REVEALED:
			return "<img class='revealed' src='/img/minesweeper/mine.png' alt='Revealed mine.'/>";
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

	public String getMineCount() {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		return formatter.format("%03d", field.getRemainingMineCount()).toString();

	}

	public String getSeconds() {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		return formatter.format("%03d", seconds).toString();
	}

	public String getGameState() {
		switch (field.getState()) {
		case PLAYING:
			return "playing";
		case FAILED:
			return "failed";
		case SOLVED:
			return "solved";
		default:
			return null;
		}
	}
}
