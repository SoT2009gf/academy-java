package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.GameRating;

public interface RatingService {

	void setRating(String game, String userName, int rating);

	GameRating getRating(String game, String userName);

	double getRatingAvg(String game);
}