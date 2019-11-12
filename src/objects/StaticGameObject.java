package objects;

import java.awt.Graphics2D;

import assets.Sprite;
import math.Vector;

public abstract class StaticGameObject extends GameObject {
	protected Sprite staticSprite;

	public StaticGameObject(Vector position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
	}

	public StaticGameObject(Vector position, Sprite sprite) {
		this.position = position;
		this.staticSprite = sprite;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
	}

	public void render(Graphics2D g) {
		if (this.staticSprite != null)
			this.staticSprite.render(g, this.getPosition());
		else
			g.draw(this.getBounds());
	}

}