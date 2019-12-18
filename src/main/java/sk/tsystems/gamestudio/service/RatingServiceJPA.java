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
	public void addRating(GameRating rating) {
		entityManager.persist(rating);

	}

	@Override
	public double getRating(String game) {
		Object result;

		result = entityManager.createQuery("select trunc(avg(g.rating), 1) from GameRating g where g.game = :game")
				.setParameter("game", game).getSingleResult();

		return result == null ? -1.0 : ((Double) result).doubleValue();
	}

	@Override
	public GameRating getRatingObject(String game, String userName) {
		try {
			return (GameRating)entityManager
					.createQuery("select g from GameRating g where g.game = :game and g.userName = :userName")
					.setParameter("game", game).setParameter("userName", userName).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public void updateRating(GameRating gameRating) {
		entityManager
				.createQuery(
						"update GameRating g set g.rating = :rating where g.game = :game and g.userName = :userName")
				.setParameter("rating", gameRating.getRating()).setParameter("game", gameRating.getGame())
				.setParameter("userName", gameRating.getUserName()).executeUpdate();
	}
}
