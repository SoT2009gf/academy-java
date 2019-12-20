package sk.tsystems.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.GameRating;
import sk.tsystems.gamestudio.service.RatingService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/setrating")
public class RatingController {

	@Autowired
	private MainController mainController;

	@Autowired
	private RatingService ratingService;

	@RequestMapping
	public String addRating(String game, String rating) {
		if (!rating.isBlank()) {
			try {
				int convertedRating = Integer.parseInt(rating);
				if (convertedRating >= 1 && convertedRating <= 5) {
					ratingService.setRating(new GameRating(mainController.getLoggedPlayer().getName(), game, convertedRating));
				}
			} catch (NumberFormatException ex) {
				return game;
			}
		}
		return game;
	}
}
