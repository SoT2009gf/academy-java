package sk.tsystems.gamestudio.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.GameRating;

@Component
@Transactional
public class RatingServiceJPA implements RatingService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void setRating(String game, String userName, int rating) {
		GameRating savedRating = getRating(game, userName);

		if (savedRating == null) {
			entityManager.persist(new GameRating(userName, game, rating));
		} else {
			entityManager.createQuery(
					"update GameRating g set g.rating = :rating where g.game = :game and g.userName = :userName")
					.setParameter("rating", rating).setParameter("game", game).setParameter("userName", userName)
					.executeUpdate();
		}
	}

	@Override
	public GameRating getRating(String game, String userName) {
		try {
			return (GameRating) entityManager
					.createQuery("select g from GameRating g where g.game = :game and g.userName = :userName")
					.setParameter("game", game).setParameter("userName", userName).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public double getRatingAvg(String game) {
		Object result;

		result = entityManager.createQuery("select avg(g.rating) from GameRating g where g.game = :game")
				.setParameter("game", game).getSingleResult();
		return result == null ? -1.0 : ((Double) result).doubleValue();
	}
}
