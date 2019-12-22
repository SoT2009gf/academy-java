package sk.tsystems.gamestudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import com.lambdaworks.crypto.SCryptUtil;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.PlayerService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/")
public class MainController {

	private Player loggedPlayer;

	private boolean registerFormVisible;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private RatingService ratingService;

	@RequestMapping
	public String index() {
		return "index";
	}

	@RequestMapping("/registerform")
	public String registerForm(String origin) {
		registerFormVisible = !registerFormVisible;
		return origin;
	}

	@RequestMapping("/registernewplayer")
	public String registerNewPlayer(String origin, Player player) {
		player.setName(player.getName().trim());
		player.setPasswd(player.getPasswd().trim());
		if (!player.getName().isEmpty() && !player.getPasswd().isEmpty()) {
			if (checkPasswdConstraints(player.getPasswd()) && player.getName().length() >= 6) {
				if (playerService.getPlayer(player.getName()) == null) {
					String hash = SCryptUtil.scrypt(player.getPasswd(), 16384, 8, 1);
					player.setPasswd(hash);
					playerService.addPlayer(player);
				}
				loggedPlayer = player;
			}
		}
		return origin;
	}

	@RequestMapping("/changepwd")
	public String changePassword(String origin, String oldpwd, String newpwd) {
		if (checkPasswdConstraints(newpwd)) {
			String newHash = SCryptUtil.scrypt(newpwd, 16384, 8, 1);
			Player player = playerService.getPlayer(loggedPlayer.getName());
			if (SCryptUtil.check(oldpwd, player.getPasswd())) {
				player.setPasswd(newHash);
				playerService.changePwd(player);
			}
		}
		return origin;
	}

	@RequestMapping("/login")
	public String login(String origin, Player player) {
		if (!player.getName().isBlank() && !player.getPasswd().isBlank()) {
			Player dbPlayer = playerService.getPlayer(player.getName());
			if (dbPlayer != null && SCryptUtil.check(player.getPasswd(), dbPlayer.getPasswd())) {
				loggedPlayer = player;
			}
		}
		return origin;
	}

	@RequestMapping("/logout")
	public String logout(String origin) {
		loggedPlayer = null;
		registerFormVisible = false;

		return origin;
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

	public List<Score> getScores(String game) {
		return scoreService.getTopScores(game);
	}

	public List<Comment> getComments(String game) {
		return commentService.getComments(game);
	}

	public double getRating(String game) {
		return ratingService.getRatingAvg(game);
	}

	private boolean checkPasswdConstraints(String passwd) {
		boolean hasDigit = false;
		boolean hasLowerCase = false;
		boolean hasUpperCase = false;
		boolean sizeOk = false;

		if (passwd.contains(" ")) {
			return false;
		}

		for (int index = 0; index < passwd.length(); index++) {
			if (Character.isDigit(passwd.charAt(index))) {
				hasDigit = true;
			}
			if (Character.isLowerCase(passwd.charAt(index))) {
				hasLowerCase = true;
			}
			if (Character.isUpperCase(passwd.charAt(index))) {
				hasUpperCase = true;
			}
		}

		if (passwd.length() >= 8 && passwd.length() <= 255) {
			sizeOk = true;
		}

		return hasDigit && hasLowerCase && hasUpperCase && sizeOk;
	}
}