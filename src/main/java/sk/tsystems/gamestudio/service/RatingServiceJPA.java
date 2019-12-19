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
	public void setRating(GameRating rating) {
		
		try {
			GameRating dbRating = (GameRating) entityManager
					.createQuery("select g from GameRating g where g.game = :game and g.userName = :userName")
					.setParameter("game", rating.getGame()).setParameter("userName", rating.getUserName())
					.getSingleResult();
			
			dbRating.setRating(rating.getRating());
		} catch (NoResultException ex) {
			entityManager.persist(rating);
		}
	}

	@Override
	public double getRatingAvg(String game) {
		Object result;

		result = entityManager.createQuery("select trunc(avg(g.rating), 1) from GameRating g where g.game = :game")
				.setParameter("game", game).getSingleResult();
		
		return result == null ? -1.0 : ((Double) result).doubleValue();
	}
}
