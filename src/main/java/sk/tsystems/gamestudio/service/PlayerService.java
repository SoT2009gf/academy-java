package sk.tsystems.gamestudio.service;

import java.util.List;

import sk.tsystems.gamestudio.entity.Player;

public interface PlayerService {
	
	void addPlayer(Player player);
	
	Player getPlayer(String userName);
	
	List<Player> getPlayers();
	
	void changePwd(Player player);
	
	void removePlayer(Player player);
}
