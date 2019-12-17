package sk.tsystems.gamestudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.guessthenumber.core.GuessTheNumberLogic;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/guessthenumber")
public class GuessTheNumberController {

	private GuessTheNumberLogic guessTheNumberLogic;

	private int guessedNumber;

	private long startMillis;

	private int bottom;

	private int top;

	@Autowired
	private MainController mainController;

	@Autowired
	private ScoreService scoreService;

	@RequestMapping
	public String index() {
		bottom = 0;
		top = 10;
		guessTheNumberLogic = new GuessTheNumberLogic(bottom, top);
		guessTheNumberLogic.thinkNumber();
		startMillis = System.currentTimeMillis();
		return "guessthenumber";
	}

	@RequestMapping("/guess")
	public String guess(String guessedNumber) {
		Score score;
		if (!isSolved()) {
			try {
				this.guessedNumber = Integer.parseInt(guessedNumber);
			} catch (NumberFormatException ex) {
				this.guessedNumber = -1;
			}
			if (isSolved() && mainController.isLogged()) {
				score = new Score(mainController.getLoggedPlayer().getName(), "guessthenumber",
						((int) ((top - bottom) * 15 - getPlayingSeconds())));
				scoreService.addScore(score);
			}
		}
		return "guessthenumber";

	}

	public boolean isSolved() {
		return guessTheNumberLogic.guess(guessedNumber) == 0;
	}

	public String getMessage() {
		String message = null;

		if (guessTheNumberLogic.guess(guessedNumber) > 0) {
			message = "Greater.";
		} else if (guessTheNumberLogic.guess(guessedNumber) < 0) {
			message = "Lower.";
		} else {
			message = "It was number " + guessedNumber + ".";
		}
		return message;
	}

	public long getPlayingSeconds() {
		return (System.currentTimeMillis() - startMillis) / 1000;
	}

	public List<Score> getScores() {
		return scoreService.getTopScores("guessthenumber");
	}
}
