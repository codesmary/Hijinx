package game;

import screen.GameScreen;

public class Game {
	public static void main(String[] s) {
		int gameWidth = 800;
		int gameHeight = 450;
		String gameName = "Hijinx";

		GameScreen game = new GameScreen(gameWidth, gameHeight, gameName);
		game.start();
	}
}