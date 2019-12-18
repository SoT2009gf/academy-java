package sk.tsystems.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.service.CommentService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/addcomment")
public class CommentController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private CommentService commentService;	
	
	@RequestMapping
	public String addComment(String game, String content) {
		if(!content.isBlank() && content.length() <= 255) {
			commentService.addComment(new Comment(mainController.getLoggedPlayer().getName(), game, content));
		}
		return "/" + game;
	}
}
