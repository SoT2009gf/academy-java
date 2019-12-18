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
@RequestMapping("/addrating")
public class RatingController {

	@Autowired
	private MainController mainController;

	@Autowired
	private RatingService ratingService;

	@RequestMapping
	public String addRating(String game, int rating) {
		if (rating >= 1 && rating <= 5) {
			GameRating gameRating = ratingService.getRatingObject(game, mainController.getLoggedPlayer().getName());
			if (gameRating == null) {
				ratingService.addRating(new GameRating(mainController.getLoggedPlayer().getName(), game, rating));
			} else {
				gameRating.setRating(rating);
				ratingService.updateRating(gameRating);
			}
		}
		return "/" + game;
	}
}
