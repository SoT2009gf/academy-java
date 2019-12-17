package sk.tsystems.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.service.PlayerService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/")
public class MainController {

	private Player loggedPlayer;

	private boolean registerFormVisible;

	@Autowired
	private PlayerService playerService;

	@RequestMapping
	public String index() {
		return "index";
	}

	@RequestMapping("/registerform")
	public String registerForm() {
		registerFormVisible = !registerFormVisible;
		return "redirect:/";
	}

	@RequestMapping("/registernewplayer")
	public String registerNewPlayer(String userName, String passwd) {
		if (playerService.getPlayer(userName) == null) {
			Player player = new Player(userName, passwd);
			playerService.addPlayer(player);
			loggedPlayer = player;
		}
		return "redirect:/";
	}

	@RequestMapping("/login")
	public String login(String name, String passwd) {
		Player player = playerService.getPlayer(name);
		if (player != null) {
			if (player.getPasswd().equals(passwd)) {
				loggedPlayer = player;
			}
		}
		return "redirect:/";
	}

	@RequestMapping("/logout")
	public String logout() {
		loggedPlayer = null;
		registerFormVisible = false;

		return "redirect:/";
	}

	public boolean isLogged() {
		return loggedPlayer != null;
	}

	public Player getLoggedPlayer() {
		return loggedPlayer;
	}

	public boolean isRegisterFormVisible() {
		return registerFormVisible;
	}
}