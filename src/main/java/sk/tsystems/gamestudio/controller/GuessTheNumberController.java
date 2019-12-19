package sk.tsystems.gamestudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.guessthenumber.core.GuessTheNumberLogic;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/guessthenumber")
public class GuessTheNumberController {

	private GuessTheNumberLogic guessTheNumberLogic;

	private Integer guessedNumber;

	private long startMillis;

	private int bottom;

	private int top;

	@Autowired
	private MainController mainController;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private RatingService ratingService;

	@RequestMapping
	public String index() {
		bottom = 0;
		top = 10;
		guessTheNumberLogic = new GuessTheNumberLogic(bottom, top);
		guessTheNumberLogic.thinkNumber();
		startMillis = System.currentTimeMillis();
		guessedNumber = null;
		return "guessthenumber";
	}

	@RequestMapping("/guess")
	public String guess(String guessedNumber) {
		if (!isSolved()) {
			try {
				this.guessedNumber = Integer.parseInt(guessedNumber);
			} catch (NumberFormatException ex) {
				this.guessedNumber = null;
			}
			if (isSolved() && mainController.isLogged()) {
				scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "guessthenumber",
						((int) ((top - bottom) * 15 - getPlayingSeconds()))));

			}
		}
		return "guessthenumber";

	}

	public boolean isSolved() {
		if (guessedNumber == null) {
			return false;
		} else {
			return guessTheNumberLogic.guess(guessedNumber) == 0;
		}
	}

	public String getMessage() {
		String message = null;

		if (guessTheNumberLogic.guess(guessedNumber) > 0) {
			message = "Greater.<br />Try again.";
		} else if (guessTheNumberLogic.guess(guessedNumber) < 0) {
			message = "Lower.<br />Try again.";
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

	public List<Comment> getComments() {
		return commentService.getComments("guessthenumber");
	}

	public double getRating() {
		return ratingService.getRatingAvg("guessthenumber");
	}

	public Integer getGuessedNumber() {
		return guessedNumber;
	}
}