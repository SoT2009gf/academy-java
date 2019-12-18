package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.GameRating;

public interface RatingService {

	void addRating(GameRating rating);

	double getRating(String game);

	GameRating getRatingObject(String game, String userName);

	void updateRating(GameRating gameRating);
}