package players;

import assets.AnimatedSprite;
import assets.Sprite;
import assets.Sprite.ImageTransformation;
import math.Vector;
import objects.DynamicGameObject;

public class Witch extends DynamicGameObject {
	private final int idleLeft = 0;
	private final int idleRight = 1;
	private final int right = 2;
	private final int left = 3;
	private final int goodMirror = 4;
	private final int flipped = 5;
	private final int brokenMirror = 6;
	private boolean lastLookedRight;

	public boolean isWitch;

	double scl = .25;

	public Witch(Vector position) {
		super(position, (int) (312 * .25), (int) (600 * .25));
		this.lastLookedRight = true;
		this.isWitch = true;

		Sprite[] idleRight = new Sprite[20];
		for (int i = 0; i < idleRight.length; i++) {
			idleRight[i] = new Sprite("/witch_idle_spritesheet.png", 0, i, 312, 600);
			idleRight[i].scaleSprite(0.25);
		}

		AnimatedSprite idleRightAnimation = new AnimatedSprite(6, idleRight);
		this.addNewSprite(idleRightAnimation);

		Sprite[] idleLeft = new Sprite[20];
		for (int i = 0; i < idleLeft.length; i++) {
			idleLeft[i] = new Sprite("/witch_idle_spritesheet.png", 0, i, 312, 600);
			idleLeft[i].scaleSprite(0.25);
			idleLeft[i].flipSprite(ImageTransformation.Horizontal);
		}

		AnimatedSprite idleLeftAnimation = new AnimatedSprite(6, idleLeft);
		this.addNewSprite(idleLeftAnimation);

		Sprite[] rightFrames = new Sprite[12];
		for (int i = 0; i < rightFrames.length; i++) {
			rightFrames[i] = new Sprite("/witch_walk_spritesheet.png", 0, i, 312, 600);
			rightFrames[i].scaleSprite(0.25);
		}

		AnimatedSprite rightAnimation = new AnimatedSprite(6, rightFrames);
		this.addNewSprite(rightAnimation);

		Sprite[] leftFrames = new Sprite[12];
		for (int i = 0; i < leftFrames.length; i++) {
			leftFrames[i] = new Sprite("/witch_walk_spritesheet.png", 0, i, 312, 600);
			leftFrames[i].scaleSprite(0.25);
			leftFrames[i].flipSprite(ImageTransformation.Horizontal);
		}

		AnimatedSprite leftAnimation = new AnimatedSprite(6, leftFrames);
		this.addNewSprite(leftAnimation);

		Sprite[] goodMirrors = new Sprite[10];
		for (int i = 0; i < goodMirrors.length; i++) {
			goodMirrors[i] = new Sprite("/mirror_spritesheet.png", 0, i, 352, 464);
			goodMirrors[i].scaleSprite(0.25);
		}

		AnimatedSprite mirrorAnimation = new AnimatedSprite(6, goodMirrors);
		this.addNewSprite(mirrorAnimation);

		Sprite[] flipped = new Sprite[10];
		for (int i = 0; i < flipped.length; i++) {
			flipped[i] = new Sprite("/mirror_spritesheet.png", 0, i, 352, 464);
			flipped[i].scaleSprite(0.25);
			flipped[i].flipSprite(ImageTransformation.Horizontal);
		}

		AnimatedSprite flippedAnimation = new AnimatedSprite(6, flipped);
		this.addNewSprite(flippedAnimation);

		Sprite[] badMirror = new Sprite[1];
		for (int i = 0; i < badMirror.length; i++) {
			badMirror[i] = new Sprite("/mirror_broken.png", 0, i, 160, 480);
			badMirror[i].scaleSprite(this.scl);
		}

		AnimatedSprite badMirrorAnimation = new AnimatedSprite(6, badMirror);
		this.addNewSprite(badMirrorAnimation);
	}

	@Override
	public void updateAnimation() {
		// System.out.println(this.isWitch);
		if (this.isWitch) {
			this.witchAnimation();
		} else {
			this.mirrorAnimation();
		}
	}

	public void transform() {
		this.isWitch = !this.isWitch;
	}

	private void witchAnimation() {
		this.setDimensions((int) (312 * .25), (int) (600 * .25));
		if (this.isMovingRight()) {
			this.lastLookedRight = false;
			this.updateSpriteTo(this.right);
		} else if (this.isMovingLeft()) {
			this.lastLookedRight = true;
			this.updateSpriteTo(this.left);
		} else if (this.lastLookedRight) {
			this.updateSpriteTo(this.idleRight);
		} else if (!this.lastLookedRight) {
			this.updateSpriteTo(this.idleLeft);
		}
	}

	public boolean overlapped = false;

	private void mirrorAnimation() {
		this.setVelocity(new Vector(0, 0));
		if (this.overlapped) {
			this.updateSpriteTo(this.brokenMirror);
			this.setDimensions((int) (160 * this.scl), (int) (480 * this.scl));
		} else {
			this.updateSpriteTo(this.goodMirror);
			this.setDimensions((int) (352 * .25), (int) (464 * .25));

		}
	}
}