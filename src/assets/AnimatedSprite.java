package assets;

import java.awt.Color;
import java.awt.Graphics2D;

import math.Vector;

//holds a collection of sprites and iterates through them
public class AnimatedSprite {
	private Sprite[] frames;
	private int delayBetweenFrames;
	private int timeForDelay;
	private int currentFrame;
	private int width;
	private int height;
	private Sprite currentSprite;

	// delay will be enumerated in number of 1/FPS "ticks" until the animation
	// changes to the next frame. for instance, if FPS = 60 and delay = 60, then it
	// would take 1 second for each frame to switch
	public AnimatedSprite(int delay, Sprite... sprites) {
		this.frames = sprites;
		this.timeForDelay = delay;
		this.width = this.frames[0].getWidth();
		this.height = this.frames[0].getHeight();
		this.currentFrame = 0;
		this.currentSprite = new Sprite();
	}

	public void tick() {
		// increase number of ticks since last frame switch
		this.delayBetweenFrames++;
		// reset delay counter to 0 if switching to new frame
		if (this.delayBetweenFrames >= this.timeForDelay) {
			this.delayBetweenFrames = 0;
			// reset frame pointer to 0 if exceeding array bounds
			if (this.currentFrame == this.frames.length)
				this.currentFrame = 0;
			// update sprite, point to next frame
			this.currentSprite = this.frames[this.currentFrame++];
		}
	}

	public void render(Graphics2D g, Vector position) {
		this.currentSprite.render(g, position);
	}

	public void flipSprite(Sprite.ImageTransformation transformation) {
		for (Sprite frame : this.frames)
			frame.flipSprite(transformation);
	}

	public void scaleSprite(int xScl, int yScl) {
		for (Sprite frame : this.frames)
			frame.scaleSprite(xScl, yScl);
	}

	public int getCurrentFrame() {
		return this.currentFrame;
	}

	public Sprite getCurrentSprite() {
		return this.currentSprite;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}