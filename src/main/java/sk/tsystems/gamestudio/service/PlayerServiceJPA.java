package sk.tsystems.gamestudio.service;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@Transactional
public class PlayerServiceJPA implements PlayerService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addPlayer(Player player) {
		entityManager.persist(player);
	}

	@Override
	public Player getPlayer(String userName) {
		try {
			return (Player) entityManager.createQuery("select p from Player p where p.name = :name")
					.setParameter("name", userName).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public void changePwd(Player player) {
		try {
			Player dbPlayer = (Player) entityManager.createQuery("select p from Player p where p.name = :name")
					.setParameter("name", player.getName()).getSingleResult();

			dbPlayer.setPasswd(player.getPasswd());
		} catch (NoResultException ex) {
		}
	}
}
