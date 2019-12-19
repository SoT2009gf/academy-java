package sk.tsystems.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

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
			ratingService.setRating(game, mainController.getLoggedPlayer().getName(), rating);
		}
		return "/" + game;
	}
}
