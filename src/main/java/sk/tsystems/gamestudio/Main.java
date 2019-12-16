package sk.tsystems.gamestudio;

import sk.tsystems.gamestudio.Menu;
import sk.tsystems.gamestudio.consoleui.ConsoleMenu;

public class Main {
	private Menu menu;

	public Main() {
		menu = new ConsoleMenu();
		menu.display();
	}

	public static void main(String[] args) {
		new Main();
	}
}
