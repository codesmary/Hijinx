package players;

import assets.AnimatedSprite;
import assets.Sprite;
import assets.Sprite.ImageTransformation;
import math.Vector;
import objects.DynamicGameObject;

public class Cat extends DynamicGameObject {
	private final int idleLeft = 0;
	private final int idleRight = 1;
	private final int right = 2;
	private final int left = 3;
	private boolean lastLookedRight;
	// private boolean emitSound;
	// private Sound thwap;

	public Cat(Vector position) {
		super(position, (int) (296 * .25), (int) (264 * .25));
		this.lastLookedRight = true;
		// int currentFrame = super.getCurrentAnimationFrame();
		// this.emitSound = !super.isMovingLeft() && !super.isMovingRight() &&
		// (currentFrame == 4 || currentFrame == 12);

		// RIP
		// this.thwap = new Sound("/bigboi.aiff");
		// this.thwap = new Sound("/sloppybassthump.wav");

		Sprite[] idleRight = new Sprite[8];
		for (int i = 0; i < idleRight.length; i++) {
			idleRight[i] = new Sprite("/cat_idle_spritesheet.png", 0, i, 224, 264);
			idleRight[i].scaleSprite(0.25);
		}

		AnimatedSprite idleRightAnimation = new AnimatedSprite(6, idleRight);
		this.addNewSprite(idleRightAnimation);

		Sprite[] idleLeft = new Sprite[8];
		for (int i = 0; i < idleLeft.length; i++) {
			idleLeft[i] = new Sprite("/cat_idle_spritesheet.png", 0, i, 224, 264);
			idleLeft[i].scaleSprite(0.25);
			idleLeft[i].flipSprite(ImageTransformation.Horizontal);
		}

		AnimatedSprite idleLeftAnimation = new AnimatedSprite(6, idleLeft);
		this.addNewSprite(idleLeftAnimation);

		Sprite[] rightFrames = new Sprite[12];
		for (int i = 0; i < rightFrames.length; i++) {
			rightFrames[i] = new Sprite("/cat_walk_spritesheet.png", 0, i, 296, 264);
			rightFrames[i].scaleSprite(0.25);
		}

		AnimatedSprite rightAnimation = new AnimatedSprite(6, rightFrames);
		this.addNewSprite(rightAnimation);

		Sprite[] leftFrames = new Sprite[12];
		for (int i = 0; i < leftFrames.length; i++) {
			leftFrames[i] = new Sprite("/cat_walk_spritesheet.png", 0, i, 296, 264);
			leftFrames[i].flipSprite(ImageTransformation.Horizontal);
			leftFrames[i].scaleSprite(0.25);
		}

		AnimatedSprite leftAnimation = new AnimatedSprite(6, leftFrames);
		this.addNewSprite(leftAnimation);
	}

	@Override
	public void updateAnimation() {
		// int currentFrame = super.getCurrentAnimationFrame();
		// this.emitSound = !super.isMovingLeft() && !super.isMovingRight() &&
		// (currentFrame == 4 || currentFrame == 12);
		// if (this.emitSound) {
		// this.thwap.play();
		// }

		if (this.isMovingRight()) {
			this.lastLookedRight = false;
			this.updateSpriteTo(this.right);
			this.setDimensions((int) (296 * .25), (int) (264 * .25));
		} else if (this.isMovingLeft()) {
			this.lastLookedRight = true;
			this.updateSpriteTo(this.left);
			this.setDimensions((int) (296 * .25), (int) (264 * .25));
		} else if (this.lastLookedRight) {
			this.setDimensions((int) (224 * .25), (int) (264 * .25));
			this.updateSpriteTo(this.idleRight);
		} else if (!this.lastLookedRight) {
			this.setDimensions((int) (224 * .25), (int) (264 * .25));
			this.updateSpriteTo(this.idleLeft);
		}
	}
}