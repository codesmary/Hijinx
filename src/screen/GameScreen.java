package screen;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import controller.GameLoop;
import view.IntroScreen;
import view.KeySet;
import view.LevelOne;

public class GameScreen extends GameLoop {
	private ArrayList<GenericScreen> screens;
	private int width;
	private int height;
	private String title;
	private int currentScreen;

	public GameScreen(int width, int height, String title) {
		super(width, height, title);

		this.screens = new ArrayList<>();
		this.width = width;
		this.height = height;
		this.title = title;

		this.addLevels();
	}

	public void addLevels() {
		IntroScreen intro = new IntroScreen(this.width, this.height);
		LevelOne one = new LevelOne(this.width, this.height);

		this.screens.add(intro);
		this.screens.add(one);

		// up down left right transform
		KeySet ijkl = new KeySet(KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_U);
		KeySet wasd = new KeySet(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q);

		one.getCat().setKeyboardControls(ijkl);
		one.getAllyOne().setKeyboardControls(wasd);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.getCurrentScreen().handleKeyPress(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.getCurrentScreen().handleKeyRelease(e);
	}

	@Override
	public void first() {
		this.getCurrentScreen().first();
	}

	@Override
	public void tick() {
		this.getCurrentScreen().tick();
	}

	@Override
	public void render(Graphics2D g) {
		this.getCurrentScreen().render(g);
	}

	private GenericScreen getCurrentScreen() {
		GenericScreen screen = this.screens.get(this.currentScreen);
		if (screen.isDoneRendering())
			this.currentScreen++;
		return this.screens.get(this.currentScreen);
	}
}