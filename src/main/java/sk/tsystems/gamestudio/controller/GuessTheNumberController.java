package sk.tsystems.gamestudio.controller;

import java.util.Formatter;

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

	private Integer guessedNumber;

	private long startMillis;

	private int bottom;

	private int top;

	private boolean solved;

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
		guessedNumber = null;
		solved = false;
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
			
			solved = guessTheNumberLogic.guess(this.guessedNumber) == 0;
			
			if (isSolved() && mainController.isLogged()) {
				int scoreValue = ((int) ((top - bottom) * 15 - getPlayingSeconds()));
				scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "guessthenumber",
						scoreValue > 0 ? scoreValue : 0));

			}
		}
		return "guessthenumber";

	}

	public String getHtmlField() {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		if (!isSolved()) {
			formatter.format("<p>I think of the number... Guess which one.</p>\n");
			formatter.format("<form action='/guessthenumber/guess'>\n");
			formatter.format(
					"<label>I'm guessing it's the number: <input type='text' name='guessedNumber' autofocus/></label>\n");
			formatter.format("<button type='submit' class='btn btn-primary'>Guess</button>\n");
			formatter.format("</form>\n");
		}
		if (guessedNumber != null) {
			formatter.format("<p>%s</p>\n", getMessage());
		}
		return formatter.toString();
	}

	public String getMessage() {
		String message;

		if (guessTheNumberLogic.guess(guessedNumber) > 0) {
			message = "Greater.<br />Try again.";
		} else if (guessTheNumberLogic.guess(guessedNumber) < 0) {
			message = "Lower.<br />Try again.";
		} else {
			message = "Congratulations. You guessed right.<br />It was number " + guessedNumber + ".";
		}
		return message;
	}

	public long getPlayingSeconds() {
		return (System.currentTimeMillis() - startMillis) / 1000;
	}

	public Integer getGuessedNumber() {
		return guessedNumber;
	}

	public boolean isSolved() {
		return solved;
	}
}