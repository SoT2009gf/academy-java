package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.GameRating;

public interface RatingService {

	void setRating(GameRating rating);

	double getRatingAvg(String game);
}