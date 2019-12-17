package sk.tsystems.gamestudio.service;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Comment;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@Transactional
public class CommentServiceJPA implements CommentService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addComment(Comment comment) {
		entityManager.persist(comment);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComments(String game) {
		try {
			return (List<Comment>) entityManager.createQuery("select c from Comment c where c.game = :game")
					.setParameter("game", game).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
