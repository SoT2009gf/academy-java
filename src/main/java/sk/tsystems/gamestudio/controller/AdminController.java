package sk.tsystems.gamestudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.service.PlayerService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private MainController mainController;

	@RequestMapping
	public String index() {
		if(isAdminLogged()) {
			return "admin";
		}
		return "redirect:/";
	}
	
	@RequestMapping("/remove")
	public String remove(String name) {
		if(isAdminLogged()) {
			Player player = playerService.getPlayer(name);
			playerService.removePlayer(player);
			return "admin";
		}
		return "redirect:/";
	}
	
	public boolean isAdminLogged() {
		return mainController.isLogged() && mainController.getLoggedPlayer().getName().equals("admin");
	}
	
	public List<Player> getPlayers(){
		return playerService.getPlayers();
	}
}
