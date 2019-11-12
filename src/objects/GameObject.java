package objects;

import java.awt.Rectangle;
import math.Vector;

public class GameObject {
	protected Vector position;
	protected int width;
	protected int height;

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setDimensions(int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
	}

	public double getPositionX() {
		return this.getPosition().x();
	}

	public double getPositionY() {
		return this.getPosition().y();
	}

	public Vector getPosition() {
		return this.position;
	}

	public void setPositionX(double x) {
		this.position.setX(x);
	}

	public void setPositionY(double y) {
		this.position.setY(y);
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) this.position.x(), (int) this.position.y(), this.width, this.height);
	}

	public boolean bottomCollision(GameObject other) {
		return this.bottomBounds().intersects(other.getBounds());
	}

	public boolean topCollision(GameObject other) {
		return this.topBounds().intersects(other.getBounds());
	}

	public boolean leftCollision(GameObject other) {
		return this.leftBounds().intersects(other.getBounds());
	}

	public boolean rightCollision(GameObject other) {
		return this.rightBounds().intersects(other.getBounds());
	}

	public Rectangle bottomBounds() {
		return new Rectangle((int) (this.getPosition().x() + this.width / 4),
				(int) (this.getPosition().y() + this.height / 2), this.width / 2, this.height / 2 + 1);
	}

	public Rectangle topBounds() {
		return new Rectangle((int) (this.getPosition().x() + this.width / 4), (int) this.getPosition().y() - 1,
				this.width / 2, this.height / 2);
	}

	public Rectangle leftBounds() {
		return new Rectangle((int) this.getPosition().x() - 1, (int) (this.getPosition().y() + this.height / 8),
				this.width / 2, 3 * this.height / 4);
	}

	public Rectangle rightBounds() {
		return new Rectangle((int) (this.getPosition().x() + this.width / 2 + 1),
				(int) (this.getPosition().y() + this.height / 8), this.width / 2, 3 * this.height / 4);
	}
}