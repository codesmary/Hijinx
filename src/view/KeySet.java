package view;

public class KeySet {
	private int up;
	private int down;
	private int left;
	private int right;
	private int transform;
	private boolean[] keyPressed;

	public KeySet(int up, int down, int left, int right, int transform) {
		this.keyPressed = new boolean[104];
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.transform = transform;
	}

	public void updateMotion(int keyVal, boolean moving) {
		this.keyPressed[keyVal] = moving;
	}

	public boolean up() {
		return this.keyPressed[this.up];
	}

	public boolean down() {
		return this.keyPressed[this.down];
	}

	public boolean left() {
		return this.keyPressed[this.left];
	}

	public boolean right() {
		return this.keyPressed[this.right];
	}

	public boolean transform() {
		return this.keyPressed[this.transform];
	}

	public boolean containedInSet(int key) {
		return key == this.up || key == this.down || key == this.left || key == this.right || key == this.transform;
	}
}