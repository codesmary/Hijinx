package controller;

import java.awt.Graphics2D;

import math.Vector;
import objects.DynamicGameObject;

enum ScreenConstraints {
	On, Off
};

// a "camera" for tracking a game object's position - simply translates
// graphics by object's position
// screen constraints allow for fixing the camera within the game bounds,
// otherwise will move freely to center the game object
public class Camera {
	private Vector pos;
	private int leftFrame, rightFrame, topFrame, bottomFrame;
	private int screenWidth;
	private int screenHeight;
	private ScreenConstraints cameraTrackingType;

	public Camera(ScreenConstraints cameraTrackingType, int screenWidth, int screenHeight) {
		this.cameraTrackingType = cameraTrackingType;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.pos = new Vector(0, 0);
	}

	public void tick(DynamicGameObject entity) {
		double x = entity.getPosition().x();
		double y = entity.getPosition().y();

		// center player in screen
		int playerPosX = (int) (this.screenWidth / 2 - x);
		int playerPosY = (int) (this.screenHeight / 2 - y);
		int camX = playerPosX;
		int camY = playerPosY;

		// don't scroll off screen if constraints toggled on
		if (this.cameraTrackingType == ScreenConstraints.On) {
			int lockRight = this.screenWidth - this.rightFrame;
			int halfScreenFromRight = this.rightFrame - this.screenWidth / 2;
			int lockBottom = this.screenHeight - this.bottomFrame;
			int halfScreenFromBottom = this.bottomFrame - this.screenHeight / 2;

			if (x > halfScreenFromRight)
				camX = lockRight;
			else if (this.leftFrame < playerPosX)
				camX = this.leftFrame;

			if (y > halfScreenFromBottom)
				camY = lockBottom;
			else if (this.topFrame < playerPosY)
				camY = this.topFrame;
		}

		this.pos.setX(camX);
		this.pos.setY(camY);
	}

	public void render(Graphics2D g) {
		g.translate(this.getPosition().x(), this.getPosition().y());
	}

	public void updateFrame(int leftFrame, int rightFrame, int topFrame, int bottomFrame) {
		this.leftFrame = leftFrame;
		this.rightFrame = rightFrame;
		this.topFrame = topFrame;
		this.bottomFrame = bottomFrame;
	}

	public Vector getPosition() {
		return this.pos;
	}
}