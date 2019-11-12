package view;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import screen.GenericScreen;

public class IntroScreen implements GenericScreen {
	private int width;
	private int height;
	private boolean beginGame;

	// TODO
	// create title screen
	// game title
	// button for starting game
	// button for credits
	// arrow keys (up and down) should toggle between these options
	// a cat should move between these options and emit "meow" sound when shifted
	// pressing enter should toggle on this specific option
	// how to play will have default settings for which set of keys to associate
	// with the players

	public IntroScreen(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void first() {

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics2D g) {

	}

	// TODO skip for now
	@Override
	public boolean isDoneRendering() {
		return true;
		// return this.beginGame;
	}

	@Override
	public void handleKeyPress(KeyEvent keyEvent) {

	}

	@Override
	public void handleKeyRelease(KeyEvent keyEvent) {

	}

}