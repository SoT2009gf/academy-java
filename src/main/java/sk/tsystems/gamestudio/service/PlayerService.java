package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Player;

public interface PlayerService {
	
	void addPlayer(Player player);
	
	Player getPlayer(String userName);
}
