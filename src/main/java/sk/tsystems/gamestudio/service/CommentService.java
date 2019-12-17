package sk.tsystems.gamestudio.service;

import java.util.List;

import sk.tsystems.gamestudio.entity.Comment;

public interface CommentService {

	void addComment(Comment comment);
	
	List<Comment> getComments(String game);
}
