package objects;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import assets.AnimatedSprite;
import controller.GameLoop;
import math.Vector;
import view.KeySet;

public abstract class DynamicGameObject extends GameObject {
	private final static Vector gravity = new Vector(0, 1000);
	private final static double damping = 0.1;
	private Vector velocity;
	private Vector acceleration;
	private Vector forceAccum;
	private double deltaTime;
	private double inverseMass;
	protected KeySet inputControls;
	protected boolean isJumping;

	protected ArrayList<AnimatedSprite> dynamicSprite;
	private int currentSprite;

	public DynamicGameObject(Vector position, int width, int height) {
		this.deltaTime = GameLoop.getSecondsPerFrame();
		this.forceAccum = new Vector(0, 0);
		this.position = position;
		this.velocity = new Vector(0, 0);
		this.acceleration = DynamicGameObject.gravity;
		this.inverseMass = 1;

		this.dynamicSprite = new ArrayList<>();
		this.width = width;
		this.height = height;
	}

	public DynamicGameObject(Vector position, Vector velocity, int width, int height) {
		this.deltaTime = GameLoop.getSecondsPerFrame();
		this.forceAccum = new Vector(0, 0);
		this.position = position;
		this.velocity = velocity;
		this.acceleration = DynamicGameObject.gravity;
		this.inverseMass = 1;

		this.dynamicSprite = new ArrayList<>();
		this.width = width;
		this.height = height;
	}

	// animated sprite constructors

	public DynamicGameObject(Vector position, AnimatedSprite sprite) {
		this.deltaTime = GameLoop.getSecondsPerFrame();
		this.forceAccum = new Vector(0, 0);
		this.position = position;
		this.velocity = new Vector(0, 0);
		this.acceleration = DynamicGameObject.gravity;
		this.inverseMass = 1;

		this.dynamicSprite = new ArrayList<>(Arrays.asList(sprite));
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
	}

	public DynamicGameObject(Vector position, Vector velocity, AnimatedSprite sprite) {
		this.deltaTime = GameLoop.getSecondsPerFrame();
		this.forceAccum = new Vector(0, 0);
		this.position = position;
		this.velocity = new Vector(0, 0);
		this.acceleration = DynamicGameObject.gravity;
		this.inverseMass = 1;

		this.dynamicSprite = new ArrayList<>(Arrays.asList(sprite));
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
	}

	public abstract void updateAnimation();

	protected boolean isMovingRight() {
		return this.getVelocityX() > 3;
	}

	protected boolean isMovingLeft() {
		return this.getVelocityX() < -3;
	}

	public void setKeyboardControls(KeySet set) {
		this.inputControls = set;
	}

	public void keyPressed(int key) {
		this.inputControls.updateMotion(key, true);
	}

	public void keyReleased(int key) {
		this.inputControls.updateMotion(key, false);
	}

	public boolean inputPressed(int key) {
		return this.inputControls.containedInSet(key);
	}

	public void updateInputForce() {
		double playerStrength = 500;
		double curVelY = this.getVelocityY();

		if (this.inputControls.up() && !this.isJumping) {
			this.isJumping = true;
			this.setVelocityY(curVelY - playerStrength);
		} else if (this.inputControls.down()) {
			this.setVelocityY(curVelY + playerStrength);
		}

		if (this.inputControls.left()) {
			this.addForce(new Vector(-playerStrength, 0));
		} else if (this.inputControls.right()) {
			this.addForce(new Vector(playerStrength, 0));
		}
	}

	// NOT including players
	public void applyScenePhysics(ArrayList<Block> screenBlocks) {
		this.integrate(this.deltaTime);
		this.pushApartSceneCollisions(screenBlocks);
	}

	public void runAnimation() {
		if (this.dynamicSprite.size() != 0)
			this.dynamicSprite.get(this.currentSprite).tick();
	}

	private double getVelocityX() {
		return this.velocity.x();
	}

	private double getVelocityY() {
		return this.velocity.y();
	}

	private void setVelocityX(double x) {
		this.velocity.setX(x);
	}

	private void setVelocityY(double y) {
		this.velocity.setY(y);
	}

	protected int getCurrentAnimationFrame() {
		if (this.dynamicSprite.size() != 0)
			return this.dynamicSprite.get(this.currentSprite).getCurrentFrame();
		else
			return -1;
	}

	private void pushApartSceneCollisions(ArrayList<Block> screenBlocks) {
		for (Block block : screenBlocks) {
			if (this.bottomCollision(block)) {
				this.isJumping = false;
				if (block.getType() == Block.BlockType.Left || block.getType() == Block.BlockType.Right) {
					this.setPositionY(block.getPositionY() + 4 - this.getHeight());
				} else {
					this.setPositionY(block.getPositionY() + 8 - this.getHeight());
				}

				this.setVelocityY(0);
			} else if (this.topCollision(block)) {
				this.setPositionY(block.getPositionY() + block.getHeight() + 1);
			} else if (this.rightCollision(block)) {
				this.setPositionX(block.getPositionX() - this.getWidth());
			} else if (this.leftCollision(block)) {
				this.setPositionX(block.getPositionX() + block.getWidth() + 1);
			}
		}
	}

	public void integrate(double deltaTime) {
		this.position = this.position.plusScaledVector(this.velocity, deltaTime);

		Vector summedAcceleration = this.acceleration;
		summedAcceleration = summedAcceleration.plusScaledVector(this.forceAccum, this.inverseMass);

		this.velocity = this.velocity.plusScaledVector(summedAcceleration, deltaTime);
		// drag
		this.velocity = this.velocity.times(Math.pow(DynamicGameObject.damping, deltaTime));

		this.clearAccumulator();
	}

	public void addForce(Vector force) {
		this.forceAccum = this.forceAccum.plus(force);
	}

	public void clearAccumulator() {
		this.forceAccum.clear();
	}

	public void render(Graphics2D g) {
		if (this.dynamicSprite.size() != 0)
			this.dynamicSprite.get(this.currentSprite).render(g, this.getPosition());
		else
			g.drawRect((int) this.getPosition().x(), (int) this.getPosition().y(), this.width, this.height);
	}

	public void addNewSprite(AnimatedSprite sprite) {
		this.dynamicSprite.add(sprite);
	}

	public void updateSpriteTo(int newSpriteIndex) {
		this.currentSprite = newSpriteIndex;
	}

	public Vector getVelocity() {
		return this.velocity;
	}

	public Vector getAcceleration() {
		return this.acceleration;
	}

	public double getMass() {
		return 1 / this.inverseMass;
	}

	public double getInverseMass() {
		return this.inverseMass;
	}

	public double getKineticEnergy() {
		return 0.5 * this.getMass() * this.velocity.magnitudeOptimized();
	}

	public boolean hasInfiniteMass() {
		return this.inverseMass <= 0;
	}

	public void setInverseMass(double inverseMass) {
		this.inverseMass = inverseMass;
	}

	public void setMass(double mass) {
		this.inverseMass = 1 / mass;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public void setAcceleration(Vector acceleration) {
		this.acceleration = acceleration;
	}

	// these are really lazy equals and hashes based on just the "particle", might
	// not work
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof DynamicGameObject))
			return false;
		else if (obj == this)
			return true;
		else {
			DynamicGameObject other = (DynamicGameObject) obj;
			return this.position.equals(other.position) && this.velocity.equals(other.velocity)
					&& this.acceleration.equals(other.acceleration);
		}
	}

	@Override
	public int hashCode() {
		int hash = this.position.hashCode() * 13 ^ this.velocity.hashCode() * 15 ^ this.acceleration.hashCode() * 19;
		return hash;
	}

}